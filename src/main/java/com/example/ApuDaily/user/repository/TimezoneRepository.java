package com.example.ApuDaily.user.repository;

import com.example.ApuDaily.user.model.Timezone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimezoneRepository extends JpaRepository<Timezone, Long> {}
