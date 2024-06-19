package com.example.renbook.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long heartNo;

    private long bookNo;
    private long memberNo;
    private boolean isDeleted;

    public Heart() {
    }

}
