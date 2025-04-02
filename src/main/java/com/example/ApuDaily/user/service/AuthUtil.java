package com.example.ApuDaily.user.service;

import org.springframework.security.core.Authentication;

public interface AuthUtil {
    Long getUserIdFromAuthentication(Authentication authentication);
}
