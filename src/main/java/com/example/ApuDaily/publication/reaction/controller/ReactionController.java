package com.example.ApuDaily.publication.reaction.controller;

import com.example.ApuDaily.publication.reaction.dto.ReactionResponseDto;
import com.example.ApuDaily.publication.reaction.dto.ReactionToggleRequestDto;
import com.example.ApuDaily.publication.reaction.service.ReactionService;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.basePath}/${api.version}/reactions")
public class ReactionController {

  @Autowired ReactionService reactionService;

  @PostMapping
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<?> toggleReaction(@Valid @RequestBody ReactionToggleRequestDto requestDto) {
    boolean isReactionSet = reactionService.toggleReaction(requestDto);
    if (isReactionSet) return ResponseEntity.status(HttpStatus.OK).build(); // Reaction set
    else return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Reaction removed
  }

  @GetMapping
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<Optional<ReactionResponseDto>> getReactionFromTarget(
      @RequestParam Long targetTypeId, @RequestParam Long entityId) {
    return ResponseEntity.ok(reactionService.getReactionFromTarget(targetTypeId, entityId));
  }
}
