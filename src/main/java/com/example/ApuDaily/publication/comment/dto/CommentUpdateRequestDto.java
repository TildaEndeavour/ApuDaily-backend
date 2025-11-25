package com.example.ApuDaily.publication.comment.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateRequestDto {
  Long commentId;
  String content;
}
