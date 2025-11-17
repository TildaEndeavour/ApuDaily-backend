package com.example.ApuDaily.publication.post.service;

import com.example.ApuDaily.publication.post.dto.*;
import org.springframework.data.domain.Page;

public interface PostService {
  Page<PostResponseDto> getPosts(int currentPageNumber, int pageSize, PostSearchRequestDto filter);

  PostResponseDto getPostById(long id);

  PostResponseDto createPost(PostCreateRequestDto requestDto);

  PostResponseDto updatePost(PostUpdateRequestDto requestDto);

  void deletePost(PostDeleteRequestDto requestDto);
}
