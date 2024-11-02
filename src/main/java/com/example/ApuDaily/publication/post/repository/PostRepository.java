package com.example.ApuDaily.publication.post.repository;

import com.example.ApuDaily.publication.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}
