package com.project.dto;

import com.project.entity.User;
import com.project.entity.enums.Gender;
import lombok.Data;

@Data
public class GuestDto {

    private Long id;

    private User user;

    private String name;

    private Gender gender;

    private Integer age;
}
