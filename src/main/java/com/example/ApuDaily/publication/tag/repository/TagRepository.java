package com.example.ApuDaily.publication.tag.repository;

import com.example.ApuDaily.publication.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
