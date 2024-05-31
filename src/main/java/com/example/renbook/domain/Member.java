package com.example.renbook.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Member {

    @Id
    private String memberId;

    private String password;
    private LocalDateTime joinDate;
}
