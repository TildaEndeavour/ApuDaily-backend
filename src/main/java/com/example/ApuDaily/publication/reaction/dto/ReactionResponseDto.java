package com.example.ApuDaily.publication.reaction.dto;

import com.example.ApuDaily.user.dto.UserProfileResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactionResponseDto {
  private Long id;

  private Long targetTypeId;

  private Long entityId;

  private Boolean isUpvote;

  private UserProfileResponseDto user;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime createdAt;
}
