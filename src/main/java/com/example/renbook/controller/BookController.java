package com.example.renbook.controller;

import com.example.renbook.domain.Book;
import com.example.renbook.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping
    public String showMainPage(Model model) {
        List<Book> bestBooks = bookService.getBestBooks();
        List<Book> newBooks = bookService.getNewBooks();

        log.info("bestBooks size={}", bestBooks.size());
        log.info("newBooks size={}", newBooks.size());

        model.addAttribute("bestBooks", bestBooks);
        model.addAttribute("newBooks", newBooks);

        return "main";
    }

    @GetMapping("/detail/{bookId}")
    public String showBookDetail(@PathVariable(name = "bookId") long bookId, Model model) {
        Book findBook = bookService.findBook(bookId);
        model.addAttribute("findBook", findBook);
        return "book_detail";
    }

    @GetMapping("/search")
    public String showSearch(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        log.info("keyword = {}", keyword);
        List<Book> findBooks = bookService.searchByKeyword(keyword);

        model.addAttribute("findBooks", findBooks);

        return "search";
    }

    @GetMapping("/rental")
    public String showRentalPage(Model model) {
        return "rental";
    }

}
