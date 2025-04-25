package com.project.service;

import com.project.dto.LoginDto;
import com.project.dto.LoginResponseDto;
import com.project.dto.SignUpRequestDto;
import com.project.dto.UserDto;

public interface AuthService {

    UserDto signUp(SignUpRequestDto signUpRequestDto);


    LoginResponseDto login(LoginDto loginDto);

    LoginResponseDto refreshToken(String refreshToken);
}
