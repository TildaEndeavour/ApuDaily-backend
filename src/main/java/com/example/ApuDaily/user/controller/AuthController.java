package com.example.ApuDaily.user.controller;

import com.example.ApuDaily.security.JwtTokenProvider;
import com.example.ApuDaily.user.dto.LoginRequestDto;
import com.example.ApuDaily.user.dto.LoginResponseDto;
import com.example.ApuDaily.user.dto.RefreshTokenRequestDto;
import com.example.ApuDaily.user.dto.RefreshTokenResponseDto;
import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.repository.RefreshTokenRepository;
import com.example.ApuDaily.user.service.AuthService;
import com.example.ApuDaily.user.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.basePath}/${api.version}/users/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto){
        LoginResponseDto responseDto = authService.login(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody RefreshTokenRequestDto requestDto) {
        String requestToken = requestDto.getRefreshToken();

        if (requestToken == null || requestToken.isBlank()) {
            return ResponseEntity.badRequest().body("Refresh token is required.");
        }

        return refreshTokenRepository.findByToken(requestToken)
                .map(token -> {
                    refreshTokenRepository.delete(token);
                    return ResponseEntity.ok("Logged out successfully.");
                })
                .orElse(ResponseEntity.badRequest().body("Invalid refresh token."));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto requestDto){
        String requestToken = requestDto.getRefreshToken();
        return refreshTokenRepository.findByToken(requestToken)
                .map(token -> {
                    if (refreshTokenService.isTokenExpired(token)) {
                        refreshTokenRepository.delete(token);
                        return ResponseEntity.badRequest().body("Refresh token expired. Please login again.");
                    }

                    User user = token.getUser();
                    String newJwt = jwtTokenProvider.generateToken(user);
                    return ResponseEntity.ok(new RefreshTokenResponseDto(newJwt));
                })
                .orElse(ResponseEntity.badRequest().body("Invalid refresh token."));
    }
}
