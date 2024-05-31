package com.example.renbook.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data

public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalId;

    private String memberId;     //멤버 id
    private Long bookId;         //도서 id
    private LocalDateTime rentalDate; //대여날짜
    private LocalDateTime returnDate; //반납날짜
    private String isRental;          //반납유무 'Y' or 'N'
}
