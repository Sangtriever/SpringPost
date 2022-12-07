package com.example.springpost.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SignupRequestDto {

    @Column(nullable = false,unique = false)
    @Size(min = 4, max = 10, message = "아이디의 길이는 4~10자 사이 입니다.")
    @Pattern(regexp = "[a-z0-9]*$",message = "아이디는 소문자,숫자만 가능합니다")
    private String username;

    @Column(nullable = false,unique = false)
    @Size(min = 8, max = 15, message = "비밀번호의 길이는 8~15자 사이 입니다.")
    @Pattern(regexp = "[A-Za-z0-9]*$",message = "비밀번호는 대or소문자, 숫자만 가능합니다")
    private String password;
//    private String email;
    private boolean admin = false;
    private String adminToken = "";
}