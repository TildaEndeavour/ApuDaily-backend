package com.example.ApuDaily.post.repository;

import com.example.ApuDaily.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}
