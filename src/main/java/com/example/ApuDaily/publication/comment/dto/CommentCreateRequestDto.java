package com.example.ApuDaily.publication.comment.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequestDto {
    private Long postId;

    private Long parentCommentId;

    private String content;
}
