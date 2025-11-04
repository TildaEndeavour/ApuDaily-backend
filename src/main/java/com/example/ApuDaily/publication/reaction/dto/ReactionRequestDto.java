package com.example.ApuDaily.publication.reaction.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactionRequestDto {
    Long targetTypeId;
    Long entityId;
}
