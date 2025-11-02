package com.example.ApuDaily.publication.reaction.service;

import com.example.ApuDaily.exception.ApiException;
import com.example.ApuDaily.exception.ErrorMessage;
import com.example.ApuDaily.publication.comment.repository.CommentRepository;
import com.example.ApuDaily.publication.post.repository.PostRepository;
import com.example.ApuDaily.publication.reaction.dto.ReactionRemoveRequestDto;
import com.example.ApuDaily.publication.reaction.dto.ReactionSetRequestDto;
import com.example.ApuDaily.publication.reaction.model.Reaction;
import com.example.ApuDaily.publication.reaction.model.ReactionType;
import com.example.ApuDaily.publication.reaction.model.TargetType;
import com.example.ApuDaily.publication.reaction.repository.ReactionRepository;
import com.example.ApuDaily.publication.reaction.repository.ReactionTypeRepository;
import com.example.ApuDaily.publication.reaction.repository.TargetTypeRepository;
import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.service.AuthUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ReactionServiceImpl implements ReactionService{

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AuthUtil authUtil;

    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    TargetTypeRepository targetTypeRepository;

    @Autowired
    ReactionTypeRepository reactionTypeRepository;

    @Override
    @Transactional
    public void setReaction(ReactionSetRequestDto requestDto){
        User user = authUtil.getUserFromAuthentication(
                SecurityContextHolder.getContext().getAuthentication());

        TargetType targetType = targetTypeRepository.findById(requestDto.getTargetTypeId())
                .orElseThrow(() -> new RuntimeException("Target type not found"));

        ReactionType reactionType = reactionTypeRepository.findById(requestDto.getReactionTypeId())
                .orElseThrow(() -> new RuntimeException("Reaction type not found"));

        reactionRepository.save(Reaction.builder()
            .user(user)
            .targetType(targetType)
            .reactionType(reactionType)
            .build());

        switch (targetType.getName()){
            case "POST": {
                switch (reactionType.getName()){
                    case "UPVOTE": postRepository.incrementUpvoteCount(requestDto.getEntityId());
                    case "DOWNVOTE": postRepository.incrementDownvoteCount(requestDto.getEntityId());
                    default:{}
                }
            }
            case "COMMENTARY": {
                switch (reactionType.getName()){
                    case "UPVOTE": commentRepository.incrementUpvoteCount(requestDto.getEntityId());
                    case "DOWNVOTE": commentRepository.incrementDownvoteCount(requestDto.getEntityId());
                    default:{}
                }
            }
            default:{}
        }
    }

    @Override
    @Transactional
    public void removeReaction(ReactionRemoveRequestDto requestDto){
        Long authenticatedUserId = authUtil.getUserIdFromAuthentication(
                SecurityContextHolder.getContext().getAuthentication());

        Reaction reaction = reactionRepository.findById(requestDto.getReactionId())
                .orElseThrow(() -> new RuntimeException("Reaction not found"));

        if(!authenticatedUserId.equals(reaction.getUser().getId())) throw new ApiException(
                ErrorMessage.USER_REACTION_MISMATCH, requestDto.getReactionId(), HttpStatus.BAD_REQUEST);

        reactionRepository.delete(reaction);

        switch (reaction.getTargetType().getName()){
            case "POST": {
                switch (reaction.getReactionType().getName()){
                    case "UPVOTE": postRepository.decrementUpvoteCount(reaction.getEntityId());
                    case "DOWNVOTE": postRepository.incrementDownvoteCount(reaction.getEntityId());
                    default:{}
                }
            }
            case "COMMENTARY": {
                switch (reaction.getReactionType().getName()){
                    case "UPVOTE": commentRepository.incrementUpvoteCount(reaction.getEntityId());
                    case "DOWNVOTE": commentRepository.incrementDownvoteCount(reaction.getEntityId());
                    default:{}
                }
            }
            default:{}
        }
    }
}
