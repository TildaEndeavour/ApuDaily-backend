package com.example.ApuDaily.publication.post.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequestDto {
  private Long postId;

  private Long thumbnailId;

  private String title;

  private Long categoryId;

  private List<Long> tagsId;

  private String description;

  private String content;
}
