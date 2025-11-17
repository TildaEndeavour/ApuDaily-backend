package com.example.ApuDaily.publication.tag.repository;

import com.example.ApuDaily.publication.tag.model.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
  Optional<Tag> findByName(String name);
}
