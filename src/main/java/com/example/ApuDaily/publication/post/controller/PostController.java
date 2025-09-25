package com.example.ApuDaily.publication.post.controller;

import com.example.ApuDaily.publication.post.dto.PostCreateRequestDto;
import com.example.ApuDaily.publication.post.dto.PostResponseDto;
import com.example.ApuDaily.publication.post.dto.PostUpdateRequestDto;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.post.service.PostService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getPostsWithFilters(
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "0") int pageNumber
    ){
        Page<Post> postPage = postService.getPosts(pageNumber,pageSize);
        return ResponseEntity.ok(postPage.map(post -> modelMapper.map(post, PostResponseDto.class)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostCreateRequestDto requestDto){
        Post post = postService.createPost(requestDto);
        return ResponseEntity.ok(modelMapper.map(post, PostResponseDto.class));
    }

    @PatchMapping("/{post_id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostResponseDto> updatePost(@Valid @RequestBody PostUpdateRequestDto requestDto){
        Post post = postService.updatePost(requestDto);
        return ResponseEntity.ok(modelMapper.map(post, PostResponseDto.class));
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable("post_id") long post_id){
        Post post = postService.getPostById(post_id);
        return ResponseEntity.ok(modelMapper.map(post, PostResponseDto.class));
    }
}
