package com.project.service;

import com.project.dto.LoginDto;
import com.project.dto.LoginResponseDto;
import com.project.dto.SignUpRequestDto;
import com.project.dto.UserDto;
import com.project.entity.User;
import com.project.entity.enums.Role;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements  AuthService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
        User user = userRepository.findByEmail(signUpRequestDto.getEmail()).orElse(null);
        if(user != null){
            throw new RuntimeException("User already exists"+ signUpRequestDto.getEmail());
        }

        User newUser = modelMapper.map(signUpRequestDto, User.class);
        newUser.setRoles(Set.of(Role.GUEST));
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        newUser = userRepository.save(newUser);

        return modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public LoginResponseDto login(LoginDto loginDto){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        User user = (User) authentication.getPrincipal();

        return LoginResponseDto.builder().id(user.getId())
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .build();

    }

    public LoginResponseDto refreshToken(String refreshToken) {
        long id =  jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String accessToken =  jwtService.generateAccessToken(user);

        return new LoginResponseDto(user.getId(), accessToken, refreshToken);
    }

}
