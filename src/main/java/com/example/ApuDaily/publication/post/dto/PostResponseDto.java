package com.example.ApuDaily.publication.post.dto;

import com.example.ApuDaily.publication.category.dto.CategoryResponseDto;
import com.example.ApuDaily.publication.category.model.Category;
import com.example.ApuDaily.publication.media.dto.MediaResponseDto;
import com.example.ApuDaily.publication.tag.dto.TagResponseDto;
import com.example.ApuDaily.publication.tag.model.Tag;
import com.example.ApuDaily.user.dto.UserResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;

    private UserResponseDto author;

    private MediaResponseDto thumbnail;

    private String title;

    private String description;

    private String content;

    private int viewCount;

    private CategoryResponseDto category;

    private List<TagResponseDto> tags;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
