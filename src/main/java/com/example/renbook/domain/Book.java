package com.example.renbook.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookNo;

    private String bookTitle;    //책제목
    private String isbn;         //isbn
    private String author;       //글쓴이
    private String publisher;    //출판사
    private LocalDate publicationDate; //출판일

    public Book() {
    }
}
