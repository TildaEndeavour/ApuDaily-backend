package com.example.ApuDaily.publication.post.service;

import com.example.ApuDaily.publication.category.model.Category;
import com.example.ApuDaily.publication.category.repository.CategoryRepository;
import com.example.ApuDaily.publication.media.model.Media;
import com.example.ApuDaily.publication.media.repository.MediaRepository;
import com.example.ApuDaily.publication.post.dto.PostCreateRequestDto;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.post.repository.PostRepository;
import com.example.ApuDaily.publication.tag.model.Tag;
import com.example.ApuDaily.publication.tag.repository.TagRepository;
import com.example.ApuDaily.security.HtmlSanitizerUtil;
import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Page<Post> getPosts(int currentPageNumber, int pageSize){
        return postRepository.findAll(PageRequest.of(currentPageNumber, pageSize));
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
}
