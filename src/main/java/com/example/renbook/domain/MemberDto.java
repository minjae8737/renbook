package com.example.renbook.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "아이디는 5~20자의 영문 대소문자, 숫자로 이루어져야 합니다.")
    private String memberId;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,16}$", message = "비밀번호는 8~16자의 영문 대소문자, 숫자, 특수문자로 이루어져야 합니다.")
    private String password;
}
