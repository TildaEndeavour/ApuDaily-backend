package com.example.ApuDaily.publication.post.service;

import com.example.ApuDaily.publication.post.dto.PostResponseDto;
import org.springframework.data.domain.Page;

public interface PostService {
    Page<PostResponseDto> getPosts(
        int currentPageNumber,
        int pageSize
    );
}
