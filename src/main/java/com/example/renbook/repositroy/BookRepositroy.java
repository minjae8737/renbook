package com.example.renbook.repositroy;

import com.example.renbook.domain.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositroy {

    private final EntityManager entityManager;

    public List<Book> findBestBooks() {
        String jpql = "SELECT b FROM Book b JOIN Rental r ON b.bookNo = r.bookNo GROUP BY b.bookNo ORDER BY COUNT (r.rentalNo) DESC";

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

    public Book findBookByBookNo(long bookNo) {
        return entityManager.find(Book.class, bookNo);
    }

    public List<RentalDto> findRentalBooksByMemberNo(long memberNo) {
        String jpql = "SELECT new com.example.renbook.domain.RentalDto(r.rentalNo, r.memberNo, r.bookNo, r.rentalDate, r.returnDate, r.isRental, r.extensionCount, " +
                "b.bookTitle, b.isbn, b.author, b.publisher, b.publicationDate)  " +
                "FROM Rental r JOIN Book b ON r.bookNo = b.bookNo " +
                "WHERE r.memberNo = :memberNo";

        return entityManager.createQuery(jpql, RentalDto.class)
                .setParameter("memberNo", memberNo)
                .getResultList();
    }

    public Optional<Rental> findRentalByRentalNo(long rentalNo) {
        Rental findRental = entityManager.find(Rental.class, rentalNo);
        return Optional.ofNullable(findRental);
    }

    public List<Rental> findRentalByBookNo(long bookNo) {
        String jpql = "SELECT r FROM Rental r WHERE r.bookNo = :bookNo AND r.isRental = 'N'";

        return entityManager.createQuery(jpql, Rental.class)
                .setParameter("bookNo", bookNo)
                .getResultList();
    }

    public void saveRent(Rental rental) {
        entityManager.persist(rental);
    }

    public void updateRental(Rental rental) {
        entityManager.merge(rental);
    }

    public List<HeartDto> findHeartByMemberNo(long memberNo) {
        String jpql = "SELECT new com.example.renbook.domain.HeartDto(h.heartNo, h.bookNo, h.bookNo, b.bookTitle, b.isbn, b.author, b.publisher, b.publicationDate, r.isRental) " +
                "FROM Heart h " +
                "JOIN Book b ON h.bookNo = b.bookNo " +
                "LEFT JOIN Rental r ON h.bookNo = r.bookNo AND r.rentalNo = (SELECT MAX (r2.rentalNo) FROM Rental r2 WHERE r2.bookNo = h.bookNo)" +
                "WHERE h.memberNo = :memberNo AND h.isDeleted = false ";

        return entityManager.createQuery(jpql, HeartDto.class)
                .setParameter("memberNo", memberNo)
                .getResultList();
    }

    public void saveHeart(Heart heart) {
        entityManager.persist(heart);
    }

    public void deleteHeart(long heartNo) {
        Heart findHeart = entityManager.find(Heart.class, heartNo);
        findHeart.setDeleted(true);
    }
}
