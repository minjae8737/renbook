package com.example.renbook.service;

import com.example.renbook.domain.Book;
import com.example.renbook.domain.Member;
import com.example.renbook.domain.Rental;
import com.example.renbook.domain.RentalDto;
import com.example.renbook.repositroy.BookRepositroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public Book findBook(long bookNo) {
        return bookRepositroy.findBookByBookNo(bookNo);
    }

    public List<RentalDto> getRentalBooksByMemberNo(Member loginMember) {
        List<RentalDto> rentalList = bookRepositroy.findRentalBooksByMemberNo(loginMember.getMemberNo());
        log.info("rentalList.size()={}",rentalList.size());
        return rentalList;
    }

    public boolean rentBook(Member loginMember, long bookNo) {
        List<Rental> findRentalList = bookRepositroy.findRentalByBookNo(bookNo);

        // rental 내용이 없다면 도서 대여 진행
        if (findRentalList.isEmpty()) {
            LocalDateTime rentalTime = LocalDateTime.now();

            Rental rental = new Rental();
            rental.setBookNo(bookNo);
            rental.setMemberNo(loginMember.getMemberNo());
            rental.setRentalDate(rentalTime);
            rental.setReturnDate(rentalTime.plusDays(14));
            rental.setIsRental("Y");
            rental.setExtensionCount(0);

            bookRepositroy.rentBookByBookNo(rental);
            return true;
        }


        return false;
    }

    public boolean returnBook(Member loginMember, long rentalNo) {
        Optional<Rental> optionalRental = bookRepositroy.findRentalByRentalNo(rentalNo);

        if (optionalRental.isEmpty()) {
            return false;
        }

        Rental findRental = optionalRental.get();

        //로그인멤버의 rental이 맞는지 검증
        if (findRental.getMemberNo() != loginMember.getMemberNo()) {
            log.info("findRental.memberNo={}  loginMember.memberNo={}", findRental.getMemberNo(), loginMember.getMemberNo());
            return false;
        }

        findRental.setIsRental("N");
        findRental.setReturnDate(LocalDateTime.now());

        bookRepositroy.returnBook(findRental);
        return true;
    }
}
