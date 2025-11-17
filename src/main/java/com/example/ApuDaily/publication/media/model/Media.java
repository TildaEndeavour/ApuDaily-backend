package com.example.ApuDaily.publication.media.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "media")
public class Media {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "post_id", unique = true, nullable = true)
  private Long postId;

  @Column(name = "status_id", unique = false, nullable = false)
  private Short statusId;

  @Column(name = "filename", unique = true, nullable = false)
  private String filename;

  @Column(name = "extension", unique = false, nullable = false)
  private String extension;

  @Column(name = "url", unique = true, nullable = false)
  private String url;

  @Column(name = "created_at", unique = false, nullable = false)
  private LocalDateTime createdAt;
}
