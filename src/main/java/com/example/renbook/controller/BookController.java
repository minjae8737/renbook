package com.example.renbook.controller;

import com.example.renbook.domain.Book;
import com.example.renbook.domain.Member;
import com.example.renbook.domain.RentalDto;
import com.example.renbook.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/detail/{bookNo}")
    public String showBookDetail(@PathVariable("bookNo") long bookNo, Model model) {
        Book findBook = bookService.findBook(bookNo);
        model.addAttribute("findBook", findBook);
        return "book_detail";
    }

    @GetMapping("/search")
    public String showSearch(@RequestParam(value = "keyword", required = false) String keyword,  Model model) {

        log.info("keyword = {}", keyword);
        List<Book> findBooks = bookService.searchByKeyword(keyword);

        model.addAttribute("findBooks", findBooks);

        return "search";
    }

    @GetMapping("/rental")
    public String showRentalPage(@SessionAttribute(name = "loginMember", required = false) Member loginMember, Model model) {

        if (loginMember == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("loginMember", loginMember);
        }

        List<RentalDto> rentalList = bookService.getRentalBooksByMemberNo(loginMember);
        model.addAttribute("rentalList", rentalList);

        return "rental";
    }

    @GetMapping("/rental/{bookNo}")
    public String rentBook(@SessionAttribute(name = "loginMember", required = false) Member loginMember, @PathVariable("bookNo") long bookNo, Model model) {

        if (loginMember == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("loginMember", loginMember);
        }

        log.info("rentbook");
        boolean isRent = bookService.rentBook(loginMember, bookNo);
        if (isRent == false) {
        }
        log.info("rentbook end");

        return "redirect:/rental";
    }

    @PostMapping("/return")
    public String returnBook(@SessionAttribute(name = "loginMember", required = false) Member loginMember, @RequestParam("rentalNo") long rentalNo, Model model) {

        if (loginMember == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("loginMember", loginMember);
        }

        log.info("return book");
        boolean isReturned = bookService.returnBook(loginMember, rentalNo);

        return "redirect:/rental";
    }
}
