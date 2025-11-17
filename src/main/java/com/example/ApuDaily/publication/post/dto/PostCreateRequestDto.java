package com.example.ApuDaily.publication.post.dto;

import com.example.ApuDaily.shared.validation.annotation.IdValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequestDto {
  @NotNull @IdValidation private Long authorId;

  @NotNull @IdValidation private Long thumbnailId;

  @NotBlank(message = "Title is mandatory")
  @Size(min = 10, max = 255, message = "Title must be between 10 and 255 characters")
  private String title;

  @NotNull @IdValidation private Long categoryId;

  @NotNull @IdValidation private List<Long> tagsId;

  @Size(max = 255, message = "Description must be less than 255 characters")
  private String description;

  @NotBlank(message = "Content is mandatory")
  @Size(min = 300, message = "Title must be more than 300 characters")
  private String content;
}
