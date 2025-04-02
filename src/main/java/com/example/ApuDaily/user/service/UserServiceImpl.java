package com.example.ApuDaily.user.service;

import com.example.ApuDaily.exception.ApiException;
import com.example.ApuDaily.exception.ErrorMessage;
import com.example.ApuDaily.user.dto.SignupRequestDto;
import com.example.ApuDaily.user.model.Role;
import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.repository.RoleRepository;
import com.example.ApuDaily.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void createUser(SignupRequestDto requestDto){
        if(userRepository.existsByEmail(requestDto.getEmail()))
            throw new ApiException(ErrorMessage.ALREADY_EXISTS, null, HttpStatus.BAD_REQUEST);

        if(userRepository.existsByUsername(requestDto.getUsername()))
            throw new ApiException(ErrorMessage.ALREADY_EXISTS, null, HttpStatus.BAD_REQUEST);

        Role role = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(
                        () -> new ApiException(ErrorMessage.ROLE_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR));

        Date date = new Date();

        User user = User.builder()
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(role)
                .createdAt(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()))
                .build();

        userRepository.save(user);
    }
}
