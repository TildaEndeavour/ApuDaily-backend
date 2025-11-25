package com.example.ApuDaily.user.service;

import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUtilImpl implements UserUtil {

  @Autowired UserRepository userRepository;

  @Override
  public Optional<User> getUserByUsernameOrEmail(String username, String email) {
    return userRepository.findByUsernameOrEmail(username, email);
  }

  @Override
  public Optional<User> getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
