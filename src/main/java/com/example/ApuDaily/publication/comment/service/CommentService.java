package com.example.ApuDaily.publication.comment.service;

import com.example.ApuDaily.publication.comment.dto.*;
import com.example.ApuDaily.publication.comment.model.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    Page<CommentResponseDto> getCommentsByFilter(int currentPageNumber, int pageSize, CommentFilter filter);
    CommentResponseDto createComment(CommentCreateRequestDto requestDto);
    Comment updateComment(CommentUpdateRequestDto requestDto);
    void deleteComment(CommentDeleteRequestDto requestDto);
}
