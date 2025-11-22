package com.example.ApuDaily.user.service;

import com.example.ApuDaily.user.dto.LoginRequestDto;
import com.example.ApuDaily.user.dto.LoginResponseDto;

public interface AuthService {
  LoginResponseDto login(LoginRequestDto requestDto);
}
