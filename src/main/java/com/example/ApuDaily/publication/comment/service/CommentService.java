package com.example.ApuDaily.publication.comment.service;

import com.example.ApuDaily.publication.comment.dto.*;
import org.springframework.data.domain.Page;

public interface CommentService {
  Page<CommentResponseDto> getCommentsByFilter(
      int currentPageNumber, int pageSize, CommentFilter filter);

  CommentResponseDto createComment(CommentCreateRequestDto requestDto);

  CommentResponseDto updateComment(CommentUpdateRequestDto requestDto);

  void deleteComment(CommentDeleteRequestDto requestDto);
}
