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
    private Long author;

    private String title;

    private String thumbnailUrl;

    private Long category;

    private List<Long> tags;

    private String description;

    //private Map<String, Object> content;
    private String content;
}
