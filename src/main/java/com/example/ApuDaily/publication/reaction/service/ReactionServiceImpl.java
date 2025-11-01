package com.example.ApuDaily.publication.reaction.service;

import com.example.ApuDaily.publication.reaction.dto.ReactionRemoveRequestDto;
import com.example.ApuDaily.publication.reaction.dto.ReactionSetRequestDto;
import com.example.ApuDaily.publication.reaction.repository.ReactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactionServiceImpl implements ReactionService{

    @Autowired
    ReactionRepository reactionRepository;

    @Override
    @Transactional
    public void setReaction(ReactionSetRequestDto requestDto){

    }

    @Override
    @Transactional
    public void removeReaction(ReactionRemoveRequestDto requestDto){

    }
}
