package com.example.ApuDaily.publication.post.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchRequestDto {
    private String searchQuery;
    private List<Integer> usersId;
    private List<Integer> tagsId;
    private Integer categoryId;
}
