package com.example.ApuDaily.user.service;

import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthUtilImpl implements AuthUtil {

  @Autowired UserRepository userRepository;

  @Override
  public Long getUserIdFromAuthentication(Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) return null;
    String usernameOrEmail = authentication.getName();
    return userRepository
        .findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
        .map(User::getId)
        .orElse(null);
  }

  @Override
  public User getUserFromAuthentication(Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) return null;
    String usernameOrEmail = authentication.getName();
    return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElse(null);
  }
}
