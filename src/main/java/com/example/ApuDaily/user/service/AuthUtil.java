package com.example.ApuDaily.user.service;

import com.example.ApuDaily.user.model.User;
import org.springframework.security.core.Authentication;

public interface AuthUtil {
  Long getUserIdFromAuthentication(Authentication authentication);

  User getUserFromAuthentication(Authentication authentication);
}
