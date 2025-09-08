package com.example.ApuDaily.publication.post.dto;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequestDto {
    private String thumbnailUrl;

    private String title;

    private Long categoryId;

    private List<Long> tagsId;

    private String description;

    private Map<String, Object> content;
}
