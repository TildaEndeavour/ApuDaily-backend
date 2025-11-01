package com.example.ApuDaily.publication.reaction.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactionSetRequestDto {
    private Long targetTypeId;
    private Long entityId;
    private Long reactionTypeId;
}
