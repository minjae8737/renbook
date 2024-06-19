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
    private Long rentalNo;

    private Long memberNo;     //멤버 id
    private Long bookNo;         //도서 id
    private LocalDateTime rentalDate; //대여날짜
    private LocalDateTime returnDate; //반납날짜
    private String isRental;          //대여유무 'Y' or 'N'
    private Integer extensionCount;      //연장횟수 최대 한번 연장가능 (+7일)

    public Rental() {
    }
}
