package com.example.ApuDaily.user.service;

import com.example.ApuDaily.user.dto.SignupRequestDto;
import com.example.ApuDaily.user.dto.UserProfileResponseDto;
import com.example.ApuDaily.user.dto.UserResponseDto;
import java.util.List;

public interface UserService {
  void createUser(SignupRequestDto requestDto);

  UserResponseDto getUserDetailsById(long userId);

  UserProfileResponseDto getUserProfileById(long userId);

  List<UserProfileResponseDto> getAllUserProfiles();
}
