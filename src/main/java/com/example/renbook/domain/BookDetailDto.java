package com.example.renbook.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDetailDto {

    private Long bookNo;

    private String bookTitle;    //책제목
    private String isbn;         //isbn
    private String author;       //글쓴이
    private String publisher;    //출판사
    private LocalDate publicationDate; //출판일
    private String isRental;          //대여유무 'Y' or 'N'

    public BookDetailDto() {
    }

    public BookDetailDto(Long bookNo, String bookTitle, String isbn, String author, String publisher, LocalDate publicationDate, String isRental) {
        this.bookNo = bookNo;
        this.bookTitle = bookTitle;
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.isRental = isRental;
    }
}
