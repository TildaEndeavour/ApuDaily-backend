package com.example.ApuDaily.user.repository;

import com.example.ApuDaily.user.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
    Optional<UserStatus> findByName(String name);
}
