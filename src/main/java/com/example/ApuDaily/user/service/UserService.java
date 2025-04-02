package com.example.ApuDaily.user.service;

import com.example.ApuDaily.user.dto.SignupRequestDto;
import com.example.ApuDaily.user.dto.UserResponseDto;

public interface UserService {
    void createUser(SignupRequestDto requestDto);
    UserResponseDto getUserDetailsById(long userId);
}
