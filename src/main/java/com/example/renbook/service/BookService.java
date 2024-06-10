package com.example.renbook.service;

import com.example.renbook.domain.Book;
import com.example.renbook.repositroy.BookRepositroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepositroy bookRepositroy;

    public List<Book> getBestBooks() {
        return bookRepositroy.findBestBooks();
    }

    public List<Book> getNewBooks() {
        LocalDate nowDate = LocalDate.now().withDayOfMonth(1);
//        log.info("nowDate={}", nowDate);
        return bookRepositroy.findNewBooks(nowDate);
    }

    public List<Book> searchByKeyword(String keyword) {
        List<Book> findBooks = bookRepositroy.searchBooks("%" + keyword + "%").orElse(null);
        return findBooks;
    }

    public Book findBook(long bookId) {
        return bookRepositroy.findBookByBookId(bookId);
    }

    public void getRentalBooks() {
    }
}
