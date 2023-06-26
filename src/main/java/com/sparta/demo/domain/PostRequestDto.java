package com.sparta.demo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    private String title;
    private String username;
    private String contents;
    private String password;

}
