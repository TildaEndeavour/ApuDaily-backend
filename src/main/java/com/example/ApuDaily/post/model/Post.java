package com.example.ApuDaily.post.model;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    private String title;

    @Column(columnDefinition = "Text")
    private String Body;

    private LocalDateTime createdAt;
}
