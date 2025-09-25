package com.example.ApuDaily.publication.post.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDeleteRequestDto {
    Long postId;
    Long userId;
}
