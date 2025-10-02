package com.example.ApuDaily.publication.post.service;

import com.example.ApuDaily.publication.post.dto.*;
import com.example.ApuDaily.publication.post.model.Post;
import org.springframework.data.domain.Page;

public interface PostService {
    Page<Post> getPosts(
        int currentPageNumber,
        int pageSize,
        PostSearchRequestDto filter
    );

    Post getPostById(long id);
    Post createPost(PostCreateRequestDto requestDto);
    Post updatePost(PostUpdateRequestDto requestDto);
    void deletePost(PostDeleteRequestDto requestDto);
}
