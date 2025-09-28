package com.example.ApuDaily.publication.post.service;

import com.example.ApuDaily.exception.ApiException;
import com.example.ApuDaily.exception.ErrorMessage;
import com.example.ApuDaily.publication.category.model.Category;
import com.example.ApuDaily.publication.category.repository.CategoryRepository;
import com.example.ApuDaily.publication.media.model.Media;
import com.example.ApuDaily.publication.media.repository.MediaRepository;
import com.example.ApuDaily.publication.post.dto.PostCreateRequestDto;
import com.example.ApuDaily.publication.post.dto.PostDeleteRequestDto;
import com.example.ApuDaily.publication.post.dto.PostUpdateRequestDto;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.post.repository.PostRepository;
import com.example.ApuDaily.publication.tag.model.Tag;
import com.example.ApuDaily.publication.tag.repository.TagRepository;
import com.example.ApuDaily.security.HtmlSanitizerUtil;
import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.repository.UserRepository;
import com.example.ApuDaily.user.service.AuthUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    AuthUtil authUtil;

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

    @Override
    @Transactional
    public Page<Post> getPosts(int currentPageNumber, int pageSize){
        return postRepository.findAll(PageRequest.of(currentPageNumber, pageSize));
    }

    @Override
    @Transactional
    public Post getPostById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Post createPost(PostCreateRequestDto requestDto){

        User user = Optional.ofNullable(requestDto.getAuthorId())
                .flatMap(authorId -> userRepository.findById(authorId))
                .orElse(null);

        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<Long> tagsId = requestDto.getTagsId();
        List<Tag> tags = tagRepository.findAllById(tagsId);
        if (tags.size() != tagsId.size()) {
            throw new RuntimeException("One or more tags not found");
        }

        Media thumbnail = Optional.ofNullable(requestDto.getThumbnailId())
                .flatMap(mediaId -> mediaRepository.findById(mediaId))
                .orElse(null);

        return postRepository.save(Post.builder()
                .user(user)
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .thumbnail(thumbnail)
                .content(HtmlSanitizerUtil.sanitize(requestDto.getContent()))
                .category(category)
                .tags(tags)
                .viewCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @Override
    @Transactional
    public Post updatePost(PostUpdateRequestDto requestDto){

        Long userId = authUtil.getUserIdFromAuthentication(
                SecurityContextHolder.getContext().getAuthentication());

        if(!userId.equals(requestDto.getAuthorId())) throw new ApiException(ErrorMessage.USER_POST_MISMATCH, requestDto.getPostId(), HttpStatus.BAD_REQUEST);

        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(() -> new ApiException(ErrorMessage.POST_NOT_FOUND, requestDto.getPostId(), HttpStatus.NOT_FOUND));

        Media thumbnail = Optional.ofNullable(requestDto.getThumbnailId())
                .flatMap(mediaId -> mediaRepository.findById(mediaId))
                .orElse(null);

        Category category = categoryRepository.findById(requestDto.getCategoryId())
                        .orElseThrow(() -> new ApiException(
                                ErrorMessage.CATEGORY_NOT_FOUND,
                                requestDto.getThumbnailId(),
                                HttpStatus.NOT_FOUND
                        ));

        post.setThumbnail(thumbnail);
        post.setTitle(requestDto.getTitle());
        post.setDescription(requestDto.getDescription());
        post.setContent(requestDto.getContent());
        post.setCategory(category);
        post.setTags(tagRepository.findAllById(requestDto.getTagsId()));
        post.setUpdatedAt(LocalDateTime.now());

        return post;
    }

    @Override
    @Transactional
    public void deletePost(PostDeleteRequestDto requestDto){
        Long authenticatedUserId = authUtil.getUserIdFromAuthentication(
                SecurityContextHolder.getContext().getAuthentication());

        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(() -> new ApiException(ErrorMessage.POST_NOT_FOUND, requestDto.getPostId(), HttpStatus.NOT_FOUND));

        if(!authenticatedUserId.equals(requestDto.getUserId())) throw new ApiException(ErrorMessage.USER_POST_MISMATCH, requestDto.getPostId(), HttpStatus.BAD_REQUEST);

        postRepository.delete(post);
    }
}
