package com.example.ApuDaily.comment.service;

import com.example.ApuDaily.publication.comment.dto.CommentCreateRequestDto;
import com.example.ApuDaily.publication.comment.dto.CommentResponseDto;
import com.example.ApuDaily.publication.comment.model.Comment;
import com.example.ApuDaily.publication.comment.repository.CommentRepository;
import com.example.ApuDaily.publication.comment.service.CommentServiceImpl;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.post.repository.PostRepository;
import com.example.ApuDaily.shared.util.DateTimeService;
import com.example.ApuDaily.testutil.DtoUtil;
import com.example.ApuDaily.testutil.TestUtil;
import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.service.AuthUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks CommentServiceImpl commentService;

    @Mock AuthUtil authUtil;

    @Mock PostRepository postRepository;

    @Mock CommentRepository commentRepository;

    @Mock DateTimeService dateTimeService;

    @Spy ModelMapper modelMapper;

    TestUtil testUtil = new TestUtil();

    DtoUtil dtoUtil = new DtoUtil();

    @Test
    public void createComment_shouldReturnResponseDto_whenValidFields(){
        // Given
        User author = testUtil.createUser(1);
        Post savedPost = testUtil.createPost(1, author);
        User anotherUser = testUtil.createUser(2);
        CommentCreateRequestDto requestDto = dtoUtil.commentCreateRequestDto(1, savedPost);
        ZonedDateTime fixedTime = ZonedDateTime.parse("2024-01-01T12:00:00Z");

        when(authUtil.getUserFromAuthentication(SecurityContextHolder.getContext().getAuthentication())).thenReturn(anotherUser);
        when(postRepository.findById(requestDto.getPostId())).thenReturn(Optional.of(savedPost));
        when(dateTimeService.getCurrentDatabaseZonedDateTime()).thenReturn(fixedTime);
        when(commentRepository.save(ArgumentMatchers.any()))
                .thenAnswer(
                        invocation -> {
                            Comment saved = invocation.getArgument(0);
                            saved.setId(1L);
                            return saved;
                        });

        // When
        CommentResponseDto responseDto = commentService.createComment(requestDto);

        // Then
        verify(postRepository, times(1)).findById(requestDto.getPostId());
        verify(commentRepository, times(1)).save(any(Comment.class));

        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(captor.capture());
        Comment saved = captor.getValue();

        assertEquals(1L, responseDto.getId());
        assertEquals(requestDto.getPostId(), responseDto.getPostId());
        assertEquals(requestDto.getParentCommentId(), responseDto.getParentCommentId());
        assertEquals(requestDto.getContent(), responseDto.getContent());
    }
}
