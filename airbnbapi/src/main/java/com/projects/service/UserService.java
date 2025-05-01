package com.projects.service;

import com.projects.dto.ProfileUpdateRequestDto;
import com.projects.dto.UserDto;
import com.projects.entity.User;

public interface UserService {

    User getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();
}
