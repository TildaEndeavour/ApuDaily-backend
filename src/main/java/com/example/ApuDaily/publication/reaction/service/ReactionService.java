package com.example.ApuDaily.publication.reaction.service;

import com.example.ApuDaily.publication.reaction.dto.ReactionRemoveRequestDto;
import com.example.ApuDaily.publication.reaction.dto.ReactionSetRequestDto;

public interface ReactionService {
    void setReaction(ReactionSetRequestDto requestDto);
    void removeReaction(ReactionRemoveRequestDto requestDto);
}
