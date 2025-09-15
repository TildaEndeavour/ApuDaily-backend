package com.example.ApuDaily.publication.post.service;

import com.example.ApuDaily.publication.post.dto.PostCreateRequestDto;
import com.example.ApuDaily.publication.post.dto.PostResponseDto;
import com.example.ApuDaily.publication.post.model.Post;
import org.springframework.data.domain.Page;

public interface PostService {
    Page<Post> getPosts(
        int currentPageNumber,
        int pageSize
    );

    Post createPost(PostCreateRequestDto requestDto);
}
