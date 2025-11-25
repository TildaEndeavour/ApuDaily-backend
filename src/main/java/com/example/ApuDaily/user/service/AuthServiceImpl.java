package com.example.ApuDaily.user.service;

import com.example.ApuDaily.exception.ApiException;
import com.example.ApuDaily.exception.ErrorMessage;
import com.example.ApuDaily.security.JwtTokenProvider;
import com.example.ApuDaily.user.dto.LoginRequestDto;
import com.example.ApuDaily.user.dto.LoginResponseDto;
import com.example.ApuDaily.user.model.RefreshToken;
import com.example.ApuDaily.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  @Autowired UserUtil userUtil;

  @Autowired AuthenticationManager authenticationManager;

  @Autowired JwtTokenProvider jwtTokenProvider;

  @Autowired RefreshTokenService refreshTokenService;

  @Override
  public LoginResponseDto login(LoginRequestDto requestDto) {
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  requestDto.getUsernameOrEmail(), requestDto.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);

      User user =
          userUtil
              .getUserByUsernameOrEmail(
                  requestDto.getUsernameOrEmail(), requestDto.getUsernameOrEmail())
              .orElseThrow(
                  () -> {
                    String message =
                        String.format(
                            "User not found with provided username or email: %s",
                            requestDto.getUsernameOrEmail());
                    return new UsernameNotFoundException(message);
                  });

      String accessToken = jwtTokenProvider.generateToken(user);
      RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

      return new LoginResponseDto(accessToken, refreshToken.getToken());
    } catch (Exception e) {
      throw new ApiException(ErrorMessage.AUTHENTICATION_ERROR, null, HttpStatus.UNAUTHORIZED);
    }
  }
}
