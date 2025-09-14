package com.example.ApuDaily.publication.post.service;

import com.example.ApuDaily.publication.category.model.Category;
import com.example.ApuDaily.publication.category.repository.CategoryRepository;
import com.example.ApuDaily.publication.media.model.Media;
import com.example.ApuDaily.publication.media.repository.MediaRepository;
import com.example.ApuDaily.publication.post.dto.PostCreateRequestDto;
import com.example.ApuDaily.publication.post.dto.PostResponseDto;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.post.repository.PostRepository;
import com.example.ApuDaily.publication.tag.model.Tag;
import com.example.ApuDaily.publication.tag.repository.TagRepository;
import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

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

    @Override
    public PostResponseDto createPost(PostCreateRequestDto requestDto){

        User user;
        if(requestDto.getAuthor() != null)
        user = userRepository.findById(requestDto.getAuthor())
                .orElseThrow(() -> new RuntimeException("User not found"));
        else user = null;

        Category category = categoryRepository.findById(requestDto.getCategory())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<Long> tagIds = requestDto.getTags();
        List<Tag> tags = tagRepository.findAllById(tagIds);
        if (tags.size() != tagIds.size()) {
            throw new RuntimeException("One or more tags not found");
        }

        Media thumbnail = mediaRepository.findByUrl(requestDto.getThumbnailUrl())
                .orElseThrow(() -> new RuntimeException("Thumbnail not found"));

        Post post = Post.builder()
                .user(user)
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .thumbnail(thumbnail)
                .content(requestDto.getContent())
                .category(category)
                .tags(tags)
                .viewCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        postRepository.save(post);

        return modelMapper.map(post, PostResponseDto.class);
    }
}
