package com.example.ApuDaily.publication.category.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
  private Long id;
  private String name;
  private String slug;
}
