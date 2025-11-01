package com.example.ApuDaily.publication.post.controller;

import com.example.ApuDaily.publication.post.dto.*;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.post.service.PostService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.basePath}/${api.version}/posts")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/search")
    public ResponseEntity<Page<PostResponseDto>> searchPosts(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber,
            @Valid @RequestBody PostSearchRequestDto filter
    ) {
        Page<PostResponseDto> postPage = postService.getPosts(pageNumber, pageSize, filter);
        return ResponseEntity.ok(postPage);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostCreateRequestDto requestDto){
        PostResponseDto result = postService.createPost(requestDto);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{post_id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostResponseDto> updatePost(@Valid @RequestBody PostUpdateRequestDto requestDto){
        PostResponseDto result = postService.updatePost(requestDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable("post_id") long post_id){
        PostResponseDto result = postService.getPostById(post_id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<?> deletePostById(@Valid @RequestBody PostDeleteRequestDto requestDto){
        postService.deletePost(requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
