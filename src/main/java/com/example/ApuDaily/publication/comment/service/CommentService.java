package com.example.ApuDaily.publication.comment.service;

import com.example.ApuDaily.publication.comment.dto.CommentCreateRequestDto;
import com.example.ApuDaily.publication.comment.dto.CommentDeleteRequestDto;
import com.example.ApuDaily.publication.comment.dto.CommentFilter;
import com.example.ApuDaily.publication.comment.dto.CommentUpdateRequestDto;
import com.example.ApuDaily.publication.comment.model.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    Page<Comment> getCommentsByFilter(int currentPageNumber, int pageSize, CommentFilter filter);
    Comment createComment(CommentCreateRequestDto requestDto);
    Comment updateComment(CommentUpdateRequestDto requestDto);
    void deleteComment(CommentDeleteRequestDto requestDto);
}
