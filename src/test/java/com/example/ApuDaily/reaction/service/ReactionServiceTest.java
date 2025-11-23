package com.example.ApuDaily.reaction.service;

import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.post.repository.PostRepository;
import com.example.ApuDaily.publication.reaction.dto.ReactionToggleRequestDto;
import com.example.ApuDaily.publication.reaction.model.Reaction;
import com.example.ApuDaily.publication.reaction.model.TargetType;
import com.example.ApuDaily.publication.reaction.repository.ReactionRepository;
import com.example.ApuDaily.publication.reaction.repository.TargetTypeRepository;
import com.example.ApuDaily.publication.reaction.service.ReactionServiceImpl;
import com.example.ApuDaily.shared.util.DateTimeService;
import com.example.ApuDaily.testutil.DtoUtil;
import com.example.ApuDaily.testutil.TestUtil;
import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.service.AuthUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReactionServiceTest {
    @InjectMocks ReactionServiceImpl reactionService;

    @Mock ReactionRepository reactionRepository;

    @Mock TargetTypeRepository targetTypeRepository;

    @Mock PostRepository postRepository;

    @Mock AuthUtil authUtil;

    @Mock DateTimeService dateTimeService;

    TestUtil testUtil = new TestUtil();

    DtoUtil dtoUtil = new DtoUtil();

    @Test
    void toggleReaction_shouldAddNewReaction_whenNoPreviousExists(){
        // Given
        User user = testUtil.createUser(1);
        Post savedPost = testUtil.createPost(1, user);
        TargetType target = TargetType.builder().id((long) 1).name("POST").build();
        Reaction savedReaction = testUtil.createReaction(1, savedPost, user);
        ZonedDateTime fixedTime = ZonedDateTime.parse("2024-01-01T12:00:00Z");
        ReactionToggleRequestDto requestDto = dtoUtil.reactionToggleRequestDto(target, savedPost.getId(), 1);

        when(authUtil.getUserFromAuthentication(SecurityContextHolder.getContext().getAuthentication())).thenReturn(user);
        when(targetTypeRepository.findById(target.getId())).thenReturn(Optional.of(target));
        when(reactionRepository.findReactionFromTarget(user.getId(), target.getId(), savedPost.getId())).thenReturn(Optional.empty());
        when(dateTimeService.getCurrentDatabaseZonedDateTime()).thenReturn(fixedTime);
        when(reactionRepository.save(any(Reaction.class))).thenReturn(savedReaction);

        // When
        boolean result = reactionService.toggleReaction(requestDto);

        // Then
        assertTrue(result);
        verify(reactionRepository).save(any(Reaction.class));
        verify(reactionRepository, never()).delete(any());
    }

    @Test
    void toggleReaction_shouldRemoveReaction_WhenMatchPrevious(){
        // Given
        User user = testUtil.createUser(1);
        Post savedPost = testUtil.createPost(1, user);
        TargetType target = TargetType.builder().id((long) 1).name("POST").build();
        Reaction savedReaction = testUtil.createReaction(1, savedPost, user);
        ReactionToggleRequestDto requestDto = dtoUtil.reactionToggleRequestDto(target, savedPost.getId(), 1);

        when(authUtil.getUserFromAuthentication(SecurityContextHolder.getContext().getAuthentication())).thenReturn(user);
        when(targetTypeRepository.findById(target.getId())).thenReturn(Optional.of(target));
        when(reactionRepository.findReactionFromTarget(user.getId(), target.getId(), savedPost.getId())).thenReturn(Optional.of(savedReaction));

        // When
        boolean result = reactionService.toggleReaction(requestDto);

        // Then
        assertFalse(result);
        verify(reactionRepository).delete(any(Reaction.class));
    }

    @Test
    void toggleReaction_shouldSwapReaction_WhenPreviousExistsAndMismatchNew(){
        // Given
        User user = testUtil.createUser(1);
        Post savedPost = testUtil.createPost(1, user);
        TargetType target = TargetType.builder().id((long) 1).name("POST").build();
        Reaction savedReaction = testUtil.createReaction(1, savedPost, user);
        Reaction newReaction = testUtil.createReaction(2, savedPost, user);
        ReactionToggleRequestDto requestDto = dtoUtil.reactionToggleRequestDto(target, savedPost.getId(), 2);
        ZonedDateTime fixedTime = ZonedDateTime.parse("2024-01-01T12:00:00Z");

        when(authUtil.getUserFromAuthentication(SecurityContextHolder.getContext().getAuthentication())).thenReturn(user);
        when(targetTypeRepository.findById(target.getId())).thenReturn(Optional.of(target));
        when(reactionRepository.findReactionFromTarget(user.getId(), target.getId(), savedPost.getId())).thenReturn(Optional.of(savedReaction));
        when(reactionRepository.save(any(Reaction.class))).thenReturn(newReaction);
        when(dateTimeService.getCurrentDatabaseZonedDateTime()).thenReturn(fixedTime);

        // When
        boolean result = reactionService.toggleReaction(requestDto);

        // Then
        assertTrue(result);
        verify(reactionRepository).delete(any(Reaction.class));
        verify(reactionRepository).save(any(Reaction.class));
    }
}
