package com.example.ApuDaily.user.service;

import com.example.ApuDaily.exception.ApiException;
import com.example.ApuDaily.exception.ErrorMessage;
import com.example.ApuDaily.security.JwtTokenProvider;
import com.example.ApuDaily.user.dto.LoginRequestDto;
import com.example.ApuDaily.user.dto.LoginResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getUsernameOrEmail(), requestDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);
            return new LoginResponseDto(token);
        } catch (Exception e) {
            throw new ApiException(ErrorMessage.AUTHENTICATION_ERROR, null, HttpStatus.UNAUTHORIZED);
        }
    }
}
