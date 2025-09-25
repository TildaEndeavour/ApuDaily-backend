package com.example.ApuDaily.publication.post.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequestDto {
    private Long postId;

    private Long authorId;

    private Long thumbnailId;

    private String title;

    private Long categoryId;

    private List<Long> tagsId;

    private String description;

    private String content;
}
