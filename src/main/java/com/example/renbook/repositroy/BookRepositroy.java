package com.example.renbook.repositroy;

import com.example.renbook.domain.Book;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositroy {

    private final EntityManager entityManager;

    public List<Book> findBestBooks() {
        String jpql = "SELECT b FROM Book b JOIN Rental r ON b.bookId = r.bookId GROUP BY b.bookId ORDER BY COUNT (r.rentalId) DESC";

        return entityManager.createQuery(jpql, Book.class).getResultList();
    }

    public List<Book> findNewBooks(LocalDate date) {
        String jpql = "SELECT b FROM Book b WHERE b.publicationDate >= :date";

        return entityManager.createQuery(jpql, Book.class).setParameter("date", date).getResultList();
    }

    public Optional<List<Book>> searchBooks(String keyword) {
        String jpql = "SELECT b FROM Book b WHERE b.bookTitle LIKE :keyword OR b.author LIKE :keyword OR b.publisher LIKE :keyword";

        List<Book> findBooks = entityManager.createQuery(jpql, Book.class)
                .setParameter("keyword", keyword)
                .getResultList();

        if (findBooks.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(findBooks);
        }
    }

    public Book findBookByBookId(long bookId) {
        return entityManager.find(Book.class, bookId);
    }

    public void getRentalBooks() {
    }

}
