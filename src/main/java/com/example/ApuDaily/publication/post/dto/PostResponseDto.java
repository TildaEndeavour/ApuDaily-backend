package com.example.ApuDaily.publication.post.dto;

import com.example.ApuDaily.publication.category.dto.CategoryResponseDto;
import com.example.ApuDaily.publication.media.dto.MediaResponseDto;
import com.example.ApuDaily.publication.tag.dto.TagResponseDto;
import com.example.ApuDaily.user.dto.UserProfileResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;

    private UserProfileResponseDto user;

    private MediaResponseDto thumbnail;

    private String title;

    private String description;

    private String content;

    private int commentariesCount;

    private int upvotesCount;

    private int downvotesCount;

    private int viewCount;

    private CategoryResponseDto category;

    private List<TagResponseDto> tags;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;
}
