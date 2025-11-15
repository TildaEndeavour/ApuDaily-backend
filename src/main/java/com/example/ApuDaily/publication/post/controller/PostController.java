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
        PostResponseDto responseDto = postService.createPost(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{post_id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostResponseDto> updatePost(@Valid @RequestBody PostUpdateRequestDto requestDto){
        PostResponseDto responseDto = postService.updatePost(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable("post_id") long post_id){
        PostResponseDto responseDto = postService.getPostById(post_id);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<?> deletePostById(@Valid @RequestBody PostDeleteRequestDto requestDto){
        postService.deletePost(requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
