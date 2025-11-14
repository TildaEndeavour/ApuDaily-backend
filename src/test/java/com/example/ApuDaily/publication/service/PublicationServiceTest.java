package com.example.ApuDaily.publication.service;

import com.example.ApuDaily.publication.category.model.Category;
import com.example.ApuDaily.publication.category.repository.CategoryRepository;
import com.example.ApuDaily.publication.media.model.Media;
import com.example.ApuDaily.publication.media.repository.MediaRepository;
import com.example.ApuDaily.publication.post.dto.PostCreateRequestDto;
import com.example.ApuDaily.publication.post.dto.PostResponseDto;
import com.example.ApuDaily.publication.post.dto.PostSearchRequestDto;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.post.repository.PostRepository;
import com.example.ApuDaily.publication.post.service.PostServiceImpl;
import com.example.ApuDaily.publication.post.specification.PostSpecification;
import com.example.ApuDaily.publication.tag.model.Tag;
import com.example.ApuDaily.publication.tag.repository.TagRepository;
import com.example.ApuDaily.shared.util.DateTimeService;
import com.example.ApuDaily.testutil.DtoUtil;
import com.example.ApuDaily.testutil.TestUtil;
import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublicationServiceTest {
    @InjectMocks
    PostServiceImpl postService;

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    TagRepository tagRepository;

    @Mock
    MediaRepository mediaRepository;

    @Mock
    DateTimeService dateTimeService;

    @Spy
    ModelMapper modelMapper;

    TestUtil testUtil = new TestUtil();

    DtoUtil dtoUtil = new DtoUtil();

    @Test
    public void createPost_shouldReturnCreatedDto_whenValidFields(){
        //Given
        User user = testUtil.createUser(1);
        Category category = testUtil.createCategory(1);
        List<Tag> tags = List.of(testUtil.createTag(1), testUtil.createTag(2));
        Media thumbnail = testUtil.createMedia(1);
        ZonedDateTime fixedTime = ZonedDateTime.parse("2024-01-01T12:00:00Z");

        PostCreateRequestDto requestDto =
                dtoUtil.postCreateRequestDto(1, category.getId(), tags.stream().map(Tag::getId).toList(), thumbnail.getId(), user);

        when(userRepository.findById(requestDto.getAuthorId())).thenReturn(Optional.of(user));
        when(categoryRepository.findById(requestDto.getCategoryId())).thenReturn(Optional.of(category));
        when(tagRepository.findAllById(requestDto.getTagsId())).thenReturn(tags);
        when(mediaRepository.findById(requestDto.getThumbnailId())).thenReturn(Optional.of(thumbnail));
        when(dateTimeService.getCurrentDatabaseZonedDateTime()).thenReturn(fixedTime);
        when(postRepository.save(ArgumentMatchers.any())).thenAnswer(invocation -> {
            Post saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        //When
        PostResponseDto postResponseDto = postService.createPost(requestDto);

        //Then
        verify(userRepository, times(1)).findById(requestDto.getAuthorId());
        verify(categoryRepository, times(1)).findById(requestDto.getCategoryId());
        verify(tagRepository, times(1)).findAllById(requestDto.getTagsId());
        verify(mediaRepository, times(1)).findById(requestDto.getThumbnailId());
        verify(dateTimeService, times(1)).getCurrentDatabaseZonedDateTime();
        verify(postRepository, times(1)).save(any(Post.class));

        ArgumentCaptor<Post> captor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(captor.capture());
        Post saved = captor.getValue();

        assertEquals(requestDto.getTitle(), saved.getTitle());
        assertEquals(user, saved.getUser());
        assertEquals(category, saved.getCategory());
        assertEquals(tags, saved.getTags());

        assertEquals(1L, postResponseDto.getId());
        assertEquals(requestDto.getTitle(), postResponseDto.getTitle());
        assertEquals(requestDto.getDescription(), postResponseDto.getDescription());
        assertEquals(requestDto.getContent(), postResponseDto.getContent());
        assertEquals(user.getId(), postResponseDto.getUser().getId());
        assertEquals(category.getId(), postResponseDto.getCategory().getId());
        assertEquals(2, postResponseDto.getTags().size());
    }

    @Test
    void getPostByFilter_shouldReturnEmptyPage_whenNoPostsFound() {
        // Given
        Integer nonExistentPostId = 1000;
        int currentPageNumber = 0;
        int pageSize = 10;

        PostSearchRequestDto postFilter =
                dtoUtil.postSearchRequestDto(null, List.of(nonExistentPostId), null, null);

        when(postRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(Page.empty());

        // When
        Page<PostResponseDto> result = postService.getPosts(currentPageNumber, pageSize, postFilter);

        // Then
        verify(postRepository, times(1))
                .findAll(any(Specification.class), any(Pageable.class));

        assertNotNull(result);
        assertTrue(result.isEmpty(), "Expected empty page when no posts found");
    }
}
