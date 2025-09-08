package com.example.ApuDaily.publication.post.controller;

import com.example.ApuDaily.publication.post.dto.PostResponseDto;
import com.example.ApuDaily.publication.post.service.PostService;
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

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getPostsWithFilters(
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "0") int pageNumber
    ){
        Page<PostResponseDto> dtoPage = postService.getPosts(pageNumber,pageSize);
        return ResponseEntity.ok(dtoPage);
    }
}
