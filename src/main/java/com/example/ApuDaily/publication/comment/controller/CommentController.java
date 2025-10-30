package com.example.ApuDaily.publication.comment.controller;

import com.example.ApuDaily.publication.comment.dto.*;
import com.example.ApuDaily.publication.comment.model.Comment;
import com.example.ApuDaily.publication.comment.service.CommentService;
import com.example.ApuDaily.publication.post.dto.PostDeleteRequestDto;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.basePath}/${api.version}/commentaries")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/search")
    public ResponseEntity<Page<CommentResponseDto>> searchComments(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber,
            @Valid @RequestBody CommentFilter filter)
    {
        Page<CommentResponseDto> response = commentService.getCommentsByFilter(pageNumber, pageSize, filter);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CommentResponseDto> createComment(@Valid @RequestBody CommentCreateRequestDto requestDto){
        CommentResponseDto result = commentService.createComment(requestDto);
        return ResponseEntity.ok(modelMapper.map(result, CommentResponseDto.class));
    }

    @PatchMapping("/{comment_id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CommentResponseDto> updateComment(@Valid @RequestBody CommentUpdateRequestDto requestDto){
        CommentResponseDto result = commentService.updateComment(requestDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{comment_id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteComment(@Valid @RequestBody CommentDeleteRequestDto requestDto){
        commentService.deleteComment(requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
