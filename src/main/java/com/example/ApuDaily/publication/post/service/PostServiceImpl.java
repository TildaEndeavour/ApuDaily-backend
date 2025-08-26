package com.example.ApuDaily.publication.post.service;

import com.example.ApuDaily.publication.post.dto.PostResponseDto;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.post.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Page<PostResponseDto> getPosts(
            int currentPageNumber,
            int pageSize
    ){
        Pageable pageable = PageRequest.of(currentPageNumber, pageSize);
        Page<Post> entityPage = postRepository.findAll(pageable);
        return entityPage
                .map(entity -> modelMapper.map(entity, PostResponseDto.class));
    }
}
