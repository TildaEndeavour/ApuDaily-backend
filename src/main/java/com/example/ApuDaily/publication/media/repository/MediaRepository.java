package com.example.ApuDaily.publication.media.repository;

import com.example.ApuDaily.publication.media.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {
    Optional<Media> findByUrl(String url);
}
