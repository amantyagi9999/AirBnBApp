package com.project.dto;

import lombok.Data;

@Data
public class SignUpRequestDto {

    private String email;
    public String password;
    public String name;
}
