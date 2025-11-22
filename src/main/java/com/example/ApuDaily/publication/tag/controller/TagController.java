package com.example.ApuDaily.publication.tag.controller;

import com.example.ApuDaily.publication.tag.dto.TagResponseDto;
import com.example.ApuDaily.publication.tag.service.TagService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${api.basePath}/${api.version}/tags")
public class TagController {

  @Autowired TagService tagService;

  @PostMapping
  public ResponseEntity<List<TagResponseDto>> createTag(@RequestBody List<String> names) {
    List<TagResponseDto> result = tagService.createTags(names);
    return ResponseEntity.ok().body(result);
  }

  @GetMapping
  public ResponseEntity<List<TagResponseDto>> getTags() {
    List<TagResponseDto> result = tagService.getAllTags();
    return ResponseEntity.ok().body(result);
  }
}
