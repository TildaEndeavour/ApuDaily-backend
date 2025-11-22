package com.example.ApuDaily.publication.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.ApuDaily.exception.ApiException;
import com.example.ApuDaily.publication.category.model.Category;
import com.example.ApuDaily.publication.category.repository.CategoryRepository;
import com.example.ApuDaily.publication.media.model.Media;
import com.example.ApuDaily.publication.media.repository.MediaRepository;
import com.example.ApuDaily.publication.post.dto.*;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.post.repository.PostRepository;
import com.example.ApuDaily.publication.post.service.PostServiceImpl;
import com.example.ApuDaily.publication.tag.model.Tag;
import com.example.ApuDaily.publication.tag.repository.TagRepository;
import com.example.ApuDaily.shared.util.DateTimeService;
import com.example.ApuDaily.testutil.DtoUtil;
import com.example.ApuDaily.testutil.TestUtil;
import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.repository.UserRepository;
import com.example.ApuDaily.user.service.AuthUtil;
import jakarta.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class PublicationServiceTest {
  @InjectMocks PostServiceImpl postService;

  @Mock PostRepository postRepository;

  @Mock UserRepository userRepository;

  @Mock CategoryRepository categoryRepository;

  @Mock TagRepository tagRepository;

  @Mock MediaRepository mediaRepository;

  @Mock DateTimeService dateTimeService;

  @Mock AuthUtil authUtil;

  @Spy ModelMapper modelMapper;

  TestUtil testUtil = new TestUtil();

  DtoUtil dtoUtil = new DtoUtil();

  @Test
  public void createPost_shouldReturnCreatedDto_whenValidFields() {
    // Given
    User user = testUtil.createUser(1);
    Category category = testUtil.createCategory(1);
    List<Tag> tags = List.of(testUtil.createTag(1), testUtil.createTag(2));
    Media thumbnail = testUtil.createMedia(1);
    ZonedDateTime fixedTime = ZonedDateTime.parse("2024-01-01T12:00:00Z");

    PostCreateRequestDto requestDto =
        dtoUtil.postCreateRequestDto(
            1, category.getId(), tags.stream().map(Tag::getId).toList(), thumbnail.getId(), user);

    when(userRepository.findById(requestDto.getAuthorId())).thenReturn(Optional.of(user));
    when(categoryRepository.findById(requestDto.getCategoryId())).thenReturn(Optional.of(category));
    when(tagRepository.findAllById(requestDto.getTagsId())).thenReturn(tags);
    when(mediaRepository.findById(requestDto.getThumbnailId())).thenReturn(Optional.of(thumbnail));
    when(dateTimeService.getCurrentDatabaseZonedDateTime()).thenReturn(fixedTime);
    when(postRepository.save(ArgumentMatchers.any()))
        .thenAnswer(
            invocation -> {
              Post saved = invocation.getArgument(0);
              saved.setId(1L);
              return saved;
            });

    // When
    PostResponseDto postResponseDto = postService.createPost(requestDto);

    // Then
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
    verify(postRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));

    assertNotNull(result);
    assertTrue(result.isEmpty(), "Expected empty page when no posts found");
  }

  @Test
  void getPostById_shouldReturnDto_whenValidId() {
    // Given
    User user = testUtil.createUser(1);
    Post post = testUtil.createPost(1, user);
    when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

    // When
    PostResponseDto responsePost = postService.getPostById(post.getId());

    // Then
    verify(postRepository, times(1)).findById(post.getId());
    assertThat(responsePost)
        .usingRecursiveComparison()
        .isEqualTo(modelMapper.map(post, PostResponseDto.class));
  }

  @Test
  void getPostById_shouldThrowNotFoundException_whenInvalidId() {
    // Given
    Long invalidId = 999L;
    when(postRepository.findById(invalidId)).thenReturn(Optional.empty());

    // When & Then
    assertThatThrownBy(() -> postService.getPostById(invalidId))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("Post with id " + invalidId + " not found");

    verify(postRepository, times(1)).findById(invalidId);
  }

  @Test
  void updatePost_shouldReturnResponseDto_whenValidFields(){
      //Given
      User author = testUtil.createUser(1);
      Post savedPost = testUtil.createPost(1, author);
      Media updatedMedia = testUtil.createMedia(2);
      Category updatedCategory = testUtil.createCategory(2);
      ZonedDateTime fixedTime = ZonedDateTime.parse("2024-01-01T12:00:00Z");
      PostUpdateRequestDto requestDto = dtoUtil.postUpdateRequestDto(savedPost, 1);

      when(authUtil.getUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication())).thenReturn(author.getId());
      when(postRepository.findById(requestDto.getPostId())).thenReturn(Optional.of(savedPost));
      when(mediaRepository.findById(requestDto.getThumbnailId())).thenReturn(Optional.of(updatedMedia));
      when(categoryRepository.findById(requestDto.getCategoryId())).thenReturn(Optional.of(updatedCategory));
      when(dateTimeService.getCurrentDatabaseZonedDateTime()).thenReturn(fixedTime);

      //When
      PostResponseDto updatedPost = postService.updatePost(requestDto);

      //Then
      verify(postRepository, times(1)).findById(requestDto.getPostId());
      verify(mediaRepository, times(1)).findById(requestDto.getThumbnailId());
      verify(categoryRepository, times(1)).findById(requestDto.getCategoryId());

      assertEquals(requestDto.getThumbnailId(), updatedPost.getThumbnail().getId());
      assertEquals(requestDto.getTitle(), updatedPost.getTitle());
      assertEquals(requestDto.getDescription(), updatedPost.getDescription());
      assertEquals(requestDto.getContent(), updatedPost.getContent());
  }

  @Test
  void updatePost_shouldThrowUserMismatchException_whenInvalidUserId(){
      //Given
      User author = testUtil.createUser(1);
      Post savedPost = testUtil.createPost(1, author);
      User anotherUser = testUtil.createUser(2);
      PostUpdateRequestDto requestDto = dtoUtil.postUpdateRequestDto(savedPost, 1);

      when(authUtil.getUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication())).thenReturn(anotherUser.getId());
      when(postRepository.findById(requestDto.getPostId())).thenReturn(Optional.of(savedPost));

      // When & Then
      assertThatThrownBy(() -> postService.updatePost(requestDto))
              .isInstanceOf(ApiException.class)
              .hasMessageContaining("Post with id %d doesn't belong to the user");

      verify(postRepository, times(1)).findById(requestDto.getPostId());
  }

  @Test
  void deletePost_shouldThrowUserMismatchException_whenInvalidUserId(){
      //Given
      User author = testUtil.createUser(1);
      Post savedPost = testUtil.createPost(1, author);
      User anotherUser = testUtil.createUser(2);
      PostDeleteRequestDto requestDto = dtoUtil.postDeleteRequestDto(savedPost);

      when(authUtil.getUserIdFromAuthentication(SecurityContextHolder.getContext().getAuthentication())).thenReturn(anotherUser.getId());
      when(postRepository.findById(requestDto.getPostId())).thenReturn(Optional.of(savedPost));

      // When & Then
      assertThatThrownBy(() -> postService.deletePost(requestDto))
              .isInstanceOf(ApiException.class)
              .hasMessageContaining("Post with id %d doesn't belong to the user");

      verify(postRepository, times(1)).findById(requestDto.getPostId());
  }
}
