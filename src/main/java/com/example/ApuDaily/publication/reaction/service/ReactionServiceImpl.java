package com.example.ApuDaily.publication.reaction.service;

import com.example.ApuDaily.publication.comment.repository.CommentRepository;
import com.example.ApuDaily.publication.post.repository.PostRepository;
import com.example.ApuDaily.publication.reaction.dto.ReactionResponseDto;
import com.example.ApuDaily.publication.reaction.dto.ReactionToggleRequestDto;
import com.example.ApuDaily.publication.reaction.model.Reaction;
import com.example.ApuDaily.publication.reaction.model.TargetType;
import com.example.ApuDaily.publication.reaction.repository.ReactionRepository;
import com.example.ApuDaily.publication.reaction.repository.TargetTypeRepository;
import com.example.ApuDaily.shared.util.DateTimeService;
import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.service.AuthUtil;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ReactionServiceImpl implements ReactionService {

  @Autowired AuthUtil authUtil;

  @Autowired ModelMapper modelMapper;

  @Autowired DateTimeService dateTimeService;

  @Autowired PostRepository postRepository;

  @Autowired CommentRepository commentRepository;

  @Autowired ReactionRepository reactionRepository;

  @Autowired TargetTypeRepository targetTypeRepository;

  @Override
  @Transactional
  public boolean toggleReaction(ReactionToggleRequestDto requestDto) {
    User authenticatedUser =
        authUtil.getUserFromAuthentication(SecurityContextHolder.getContext().getAuthentication());

    TargetType targetType =
        targetTypeRepository
            .findById(requestDto.getTargetTypeId())
            .orElseThrow(() -> new RuntimeException("Target type not found"));

    Optional<Reaction> optionalPrevReaction =
        reactionRepository.findReactionFromTarget(
            authenticatedUser.getId(), requestDto.getTargetTypeId(), requestDto.getEntityId());

    // Checking if a reaction from the user already exists
    if (optionalPrevReaction.isPresent()) {

      // Deleting prev reaction
      reactionRepository.delete(optionalPrevReaction.get());

      // Update reaction counter
      updateCounter(
          false,
          optionalPrevReaction.get().getIsUpvote(),
          optionalPrevReaction.get().getTargetType().getName(),
          optionalPrevReaction.get().getEntityId());

      // Checking if the previous reaction matches the new one
      if (optionalPrevReaction.get().getIsUpvote().equals(requestDto.getIsUpvote())) return false;
    }

    // The previous reaction does not match the new one
    // Saving the new one
    Reaction reaction =
        reactionRepository.save(
            Reaction.builder()
                .user(authenticatedUser)
                .targetType(targetType)
                .entityId(requestDto.getEntityId())
                .isUpvote(requestDto.getIsUpvote())
                .createdAt(dateTimeService.getCurrentDatabaseZonedDateTime().toLocalDateTime())
                .build());

    // Update counter after add new reaction
    updateCounter(
        true, reaction.getIsUpvote(), reaction.getTargetType().getName(), reaction.getEntityId());

    return true;
  }

  @Override
  @Transactional
  public Optional<ReactionResponseDto> getReactionFromTarget(Long targetTypeId, Long entityId) {
    User authenticatedUser =
        authUtil.getUserFromAuthentication(SecurityContextHolder.getContext().getAuthentication());

    Optional<Reaction> userReaction =
        reactionRepository.findReactionFromTarget(
            authenticatedUser.getId(), targetTypeId, entityId);

    return userReaction.map(reaction -> modelMapper.map(reaction, ReactionResponseDto.class));
  }

  private void updateCounter(
      Boolean isIncrement, boolean isUpvote, String targetTypeName, Long entityId) {
    switch (targetTypeName) {
      case "POST" -> {
        if (isIncrement) {
          if (isUpvote) postRepository.incrementUpvoteCount(entityId);
          else postRepository.incrementDownvoteCount(entityId);
        } else {
          if (isUpvote) postRepository.decrementUpvoteCount(entityId);
          else postRepository.decrementDownvoteCount(entityId);
        }
      }
      case "COMMENTARY" -> {
        if (isIncrement) {
          if (isUpvote) commentRepository.incrementUpvoteCount(entityId);
          else commentRepository.incrementDownvoteCount(entityId);
        } else {
          if (isUpvote) commentRepository.decrementUpvoteCount(entityId);
          else commentRepository.decrementDownvoteCount(entityId);
        }
      }
    }
  }
}
