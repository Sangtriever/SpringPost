package com.example.springpost.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {
    private String name;
    private String contents;
    private String password;
    private String title;
}
