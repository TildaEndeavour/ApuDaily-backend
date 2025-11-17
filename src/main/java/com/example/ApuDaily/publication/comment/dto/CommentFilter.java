package com.example.ApuDaily.publication.comment.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentFilter {
  private Long postId;
  private Long userId;
  private Long parentCommentId;
  private Long commentId;
}
