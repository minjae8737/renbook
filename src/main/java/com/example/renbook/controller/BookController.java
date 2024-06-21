package com.example.renbook.controller;

import com.example.renbook.domain.Book;
import com.example.renbook.domain.HeartDto;
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
    public String showSearch(@RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             Model model) {
        log.info("keyword = {}", keyword);
        log.info("curPageNum={}", page);

        int pageSize = 10;
        List<Book> findBooks = bookService.searchByKeyword(keyword);


        int totalBooks = findBooks.size();
        int totalPage = (int) Math.ceil((double)findBooks.size() / (double) pageSize);

        //현재 page에서 보여줄 findBooks 범위
        int startBookIdx = (page - 1) * pageSize;
        int endBookIdx = Math.min(findBooks.size(), (startBookIdx + pageSize));

        findBooks = findBooks.subList(startBookIdx, endBookIdx);

        //현재 page 기준에 따라서 보여줄 page 범위
        int startPage = page < 7 ? 1 : page - 4;
        int endPage = page + 5 < totalPage ?  page + 5 : totalPage;

        //페이징
        //페이지당 40권씩 보여주기
        model.addAttribute("keyword", keyword);
        model.addAttribute("findBooks", findBooks);
        model.addAttribute("curPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "search";
    }

    @GetMapping("/rental")
    public String showRentalPage(@SessionAttribute(name = "loginMember", required = false) Member loginMember, Model model) {

        List<RentalDto> rentalList = bookService.getRentalBooksByMemberNo(loginMember);
        model.addAttribute("rentalList", rentalList);

        return "rental";
    }

    @GetMapping("/rental/{bookNo}")
    public String rentBook(@SessionAttribute(name = "loginMember", required = false) Member loginMember, @PathVariable("bookNo") long bookNo, Model model) {

        log.info("rentbook");
        boolean isRent = bookService.rentBook(loginMember, bookNo);
        if (isRent == false) {
        }
        log.info("rentbook end");

        return "redirect:/rental";
    }

    @PostMapping("/return")
    public String returnBook(@SessionAttribute(name = "loginMember", required = false) Member loginMember, @RequestParam("rentalNo") long rentalNo, Model model) {

        log.info("return book");
        boolean isReturned = bookService.returnBook(loginMember, rentalNo);

        return "redirect:/rental";
    }

    @GetMapping("/heart")
    public String showHeartPage(@SessionAttribute(name = "loginMember", required = false) Member loginMember, Model model) {

        List<HeartDto> heartBookList = bookService.getHeartBooksByMemberNo(loginMember);
        model.addAttribute("heartBookList", heartBookList);

        return "heart";
    }

    @GetMapping("/heart/{bookNo}")
    public String addHeart(@SessionAttribute(name = "loginMember", required = false) Member loginMember, @PathVariable("bookNo") long bookNo, Model model) {
        bookService.addHeart(loginMember, bookNo);

        return "redirect:/";
    }

    @PostMapping("/heart/delete")
    public String deleteHeart(@RequestParam("heartNo") long heartNo) {

        bookService.deleteHeart(heartNo);

        return "redirect:/heart";
    }
}
