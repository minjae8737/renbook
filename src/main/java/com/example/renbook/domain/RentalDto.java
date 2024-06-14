package com.example.renbook.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RentalDto {

    private Long rentalNo;
    private Long memberNo;     //멤버 id
    private Long bookNo;         //도서 id
    private LocalDateTime rentalDate; //대여날짜
    private LocalDateTime returnDate; //반납날짜
    private String isRental;          //반납유무 'Y' or 'N'
    private Integer extensionCount;      //연장횟수 최대 한번 연장가능 (+7일)

    private String bookTitle;    //책제목
    private String isbn;         //isbn
    private String author;       //글쓴이
    private String publisher;    //출판사
    private LocalDate publicationDate; //출판일

    public RentalDto(Long rentalNo, Long memberNo, Long bookNo, LocalDateTime rentalDate, LocalDateTime returnDate, String isRental, Integer extensionCount, String bookTitle, String isbn, String author, String publisher, LocalDate publicationDate) {
        this.rentalNo = rentalNo;
        this.memberNo = memberNo;
        this.bookNo = bookNo;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.isRental = isRental;
        this.extensionCount = extensionCount;
        this.bookTitle = bookTitle;
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
    }
}
