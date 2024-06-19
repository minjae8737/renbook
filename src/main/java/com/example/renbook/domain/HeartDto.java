package com.example.renbook.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HeartDto {

    private long heartNo;

    private long bookNo;
    private long memberNo;

    private String bookTitle;    //책제목
    private String isbn;         //isbn
    private String author;       //글쓴이
    private String publisher;    //출판사
    private LocalDate publicationDate; //출판일

    private String isRental;          //반납유무 'Y' or 'N'

    public HeartDto(long heartNo, long bookNo, long memberNo, String bookTitle, String isbn, String author, String publisher, LocalDate publicationDate, String isRental) {
        this.heartNo = heartNo;
        this.bookNo = bookNo;
        this.memberNo = memberNo;
        this.bookTitle = bookTitle;
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.isRental = isRental;
    }
}
