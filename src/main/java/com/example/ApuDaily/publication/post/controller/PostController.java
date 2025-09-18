package com.example.ApuDaily.publication.post.controller;

import com.example.ApuDaily.publication.post.dto.PostCreateRequestDto;
import com.example.ApuDaily.publication.post.dto.PostResponseDto;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.post.service.PostService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
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

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<PostResponseDto> createPost(@Valid @ModelAttribute PostCreateRequestDto requestDto){
        Post post = postService.createPost(requestDto);
        return ResponseEntity.ok(modelMapper.map(post, PostResponseDto.class));
    }
}
