package com.example.ApuDaily.publication.reaction.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactionToggleRequestDto {
  private Long targetTypeId;
  private Long entityId;
  private Boolean isUpvote;
}
