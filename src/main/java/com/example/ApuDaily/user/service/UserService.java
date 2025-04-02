package com.example.ApuDaily.user.service;

import com.example.ApuDaily.user.dto.SignupRequestDto;

public interface UserService {
    void createUser(SignupRequestDto requestDto);
}
