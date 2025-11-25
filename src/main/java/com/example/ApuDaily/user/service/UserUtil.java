package com.example.ApuDaily.user.service;

import com.example.ApuDaily.user.model.User;
import java.util.Optional;

public interface UserUtil {
  Optional<User> getUserByUsernameOrEmail(String username, String email);

  Optional<User> getUserByUsername(String username);

  Optional<User> getUserByEmail(String email);
}
