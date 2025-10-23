package com.example.ApuDaily.publication.comment.service;

import com.example.ApuDaily.exception.ApiException;
import com.example.ApuDaily.exception.ErrorMessage;
import com.example.ApuDaily.publication.comment.dto.*;
import com.example.ApuDaily.publication.comment.model.Comment;
import com.example.ApuDaily.publication.comment.repository.CommentRepository;
import com.example.ApuDaily.publication.comment.specification.CommentSpecification;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.post.repository.PostRepository;
import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.repository.UserRepository;
import com.example.ApuDaily.user.service.AuthUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthUtil authUtil;

    @Autowired
    PostRepository postRepository;

    @Override
    @Transactional
    public Page<CommentResponseDto> getCommentsByFilter(int currentPageNumber, int pageSize, CommentFilter filter){
        Specification<Comment> spec = null;

        if(filter != null && (filter.getPostId() != null ||
                              filter.getUserId() != null ||
                              filter.getParentCommentId() != null ||
                              filter.getCommentId() != null))
        {
            spec = CommentSpecification.withFilters(filter);
        }

        Page<Comment> commentsPage = commentRepository.findAll(spec, PageRequest.of(currentPageNumber, pageSize));
        return commentsPage.map(comment -> modelMapper.map(comment, CommentResponseDto.class));
    }

    @Override
    @Transactional
    public Comment createComment(CommentCreateRequestDto requestDto){

        User user = authUtil.getUserFromAuthentication(
                SecurityContextHolder.getContext().getAuthentication());

        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(() -> new ApiException(ErrorMessage.POST_NOT_FOUND, requestDto.getPostId(), HttpStatus.BAD_REQUEST));

        Comment parentComment = Optional.ofNullable(requestDto.getParentCommentId())
                .map(id -> commentRepository.findById(id)
                        .orElseThrow(() -> new ApiException(
                                ErrorMessage.COMMENT_NOT_FOUND,
                                id,
                                HttpStatus.BAD_REQUEST)))
                .orElse(null);

        return commentRepository.save(Comment.builder()
                .user(user)
                .post(post)
                .content(requestDto.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .parentComment(parentComment)
                .build());
    }

    @Override
    @Transactional
    public Comment updateComment(CommentUpdateRequestDto requestDto){

        Long userId = authUtil.getUserIdFromAuthentication(
                SecurityContextHolder.getContext().getAuthentication());

        if(!userId.equals(requestDto.getUserId())) throw new ApiException(ErrorMessage.USER_COMMENT_MISMATCH, requestDto.getCommentId(), HttpStatus.BAD_REQUEST);

        Comment comment = commentRepository.findById(requestDto.getCommentId())
                .orElseThrow(() -> new ApiException(ErrorMessage.COMMENT_NOT_FOUND, requestDto.getCommentId(), HttpStatus.BAD_REQUEST));

        comment.setContent(requestDto.getContent());
        comment.setUpdatedAt(LocalDateTime.now());

        return comment;
    }

    @Override
    @Transactional
    public void deleteComment(CommentDeleteRequestDto requestDto){
        Long authenticatedUserId = authUtil.getUserIdFromAuthentication(
                SecurityContextHolder.getContext().getAuthentication());

        Comment comment = commentRepository.findById(requestDto.getCommentId())
                .orElseThrow(() -> new ApiException(ErrorMessage.COMMENT_NOT_FOUND, requestDto.getCommentId(), HttpStatus.BAD_REQUEST));

        if(!authenticatedUserId.equals(requestDto.getUserId())) throw new ApiException(ErrorMessage.USER_POST_MISMATCH, requestDto.getCommentId(), HttpStatus.BAD_REQUEST);

        commentRepository.delete(comment);
    }
}
