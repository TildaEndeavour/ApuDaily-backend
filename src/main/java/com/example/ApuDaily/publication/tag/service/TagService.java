package com.example.ApuDaily.publication.tag.service;

import com.example.ApuDaily.publication.tag.dto.TagResponseDto;
import java.util.List;

public interface TagService {
  List<TagResponseDto> createTags(List<String> name);

  List<TagResponseDto> getAllTags();
}
