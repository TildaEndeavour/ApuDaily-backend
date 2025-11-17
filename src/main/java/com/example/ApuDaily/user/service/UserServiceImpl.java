package com.example.ApuDaily.user.service;

import com.example.ApuDaily.exception.ApiException;
import com.example.ApuDaily.exception.ErrorMessage;
import com.example.ApuDaily.shared.util.DateTimeService;
import com.example.ApuDaily.user.dto.SignupRequestDto;
import com.example.ApuDaily.user.dto.UserProfileResponseDto;
import com.example.ApuDaily.user.dto.UserResponseDto;
import com.example.ApuDaily.user.model.Role;
import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.repository.RoleRepository;
import com.example.ApuDaily.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired UserRepository userRepository;

  @Autowired RoleRepository roleRepository;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired ModelMapper modelMapper;

  @Autowired DateTimeService dateTimeService;

  @Override
  public void createUser(SignupRequestDto requestDto) {
    if (userRepository.existsByEmail(requestDto.getEmail()))
      throw new ApiException(ErrorMessage.ALREADY_EXISTS, null, HttpStatus.BAD_REQUEST);

    if (userRepository.existsByUsername(requestDto.getUsername()))
      throw new ApiException(ErrorMessage.ALREADY_EXISTS, null, HttpStatus.BAD_REQUEST);

    Role role =
        roleRepository
            .findByName("ROLE_USER")
            .orElseThrow(
                () ->
                    new ApiException(
                        ErrorMessage.ROLE_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR));

    User user =
        User.builder()
            .username(requestDto.getUsername())
            .email(requestDto.getEmail())
            .password(passwordEncoder.encode(requestDto.getPassword()))
            .role(role)
            .createdAt(dateTimeService.getCurrentDatabaseZonedDateTime().toLocalDateTime())
            .build();

    userRepository.save(user);
  }

  @Override
  public UserResponseDto getUserDetailsById(long userId) {
    return userRepository
        .findById(userId)
        .map(user -> modelMapper.map(user, UserResponseDto.class))
        .orElseThrow(
            () -> new ApiException(ErrorMessage.USER_NOT_FOUND, userId, HttpStatus.NOT_FOUND));
  }

  @Override
  public UserProfileResponseDto getUserProfileById(long userId) {
    return userRepository
        .findById(userId)
        .map(user -> modelMapper.map(user, UserProfileResponseDto.class))
        .orElseThrow(
            () -> new ApiException(ErrorMessage.USER_NOT_FOUND, userId, HttpStatus.NOT_FOUND));
  }

  @Override
  public List<UserProfileResponseDto> getAllUserProfiles() {
    return userRepository.findAll().stream()
        .map(user -> modelMapper.map(user, UserProfileResponseDto.class))
        .collect(Collectors.toList());
  }
}
