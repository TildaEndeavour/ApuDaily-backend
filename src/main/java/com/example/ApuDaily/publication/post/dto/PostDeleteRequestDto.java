package com.example.ApuDaily.publication.post.dto;

import com.example.ApuDaily.shared.validation.annotation.IdValidation;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDeleteRequestDto {
  @NotNull @IdValidation Long postId;
}
