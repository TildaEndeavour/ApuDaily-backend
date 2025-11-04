package com.example.ApuDaily.publication.reaction.service;

import com.example.ApuDaily.publication.reaction.dto.ReactionRequestDto;
import com.example.ApuDaily.publication.reaction.dto.ReactionToggleRequestDto;
import com.example.ApuDaily.publication.reaction.model.Reaction;

import java.util.Optional;

public interface ReactionService {
    boolean toggleReaction(ReactionToggleRequestDto requestDto);
    Optional<Reaction> getReactionFromTarget(ReactionRequestDto requestDto);
}
