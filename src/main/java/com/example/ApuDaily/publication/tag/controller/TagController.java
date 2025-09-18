package com.example.ApuDaily.publication.tag.controller;

import com.example.ApuDaily.publication.tag.dto.TagResponseDto;
import com.example.ApuDaily.publication.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("${api.basePath}/${api.version}/tags")
public class TagController {

    @Autowired
    TagService tagService;

    @PostMapping
    public ResponseEntity<List<TagResponseDto>> createTag(
            @RequestBody List<String> names
    ){
        List<TagResponseDto> result = tagService.createTags(names);
        return ResponseEntity.ok().body(result);
    }
}
