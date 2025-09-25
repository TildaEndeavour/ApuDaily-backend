package com.example.ApuDaily.user.service;

import com.example.ApuDaily.user.model.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long userId);
    boolean isTokenExpired(RefreshToken token);
}
