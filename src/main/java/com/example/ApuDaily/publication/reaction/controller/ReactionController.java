package com.example.ApuDaily.publication.reaction.controller;

import com.example.ApuDaily.publication.reaction.dto.ReactionRemoveRequestDto;
import com.example.ApuDaily.publication.reaction.dto.ReactionSetRequestDto;
import com.example.ApuDaily.publication.reaction.repository.ReactionRepository;
import com.example.ApuDaily.publication.reaction.service.ReactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.basePath}/${api.version}/reactions")
public class ReactionController {

    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    ReactionService reactionService;

    @PostMapping
    public ResponseEntity<?> setReaction(@Valid @RequestBody ReactionSetRequestDto requestDto){
        reactionService.setReaction(requestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping
    public ResponseEntity<?> removeReaction(@Valid @RequestBody ReactionRemoveRequestDto requestDto){
        reactionService.removeReaction(requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
