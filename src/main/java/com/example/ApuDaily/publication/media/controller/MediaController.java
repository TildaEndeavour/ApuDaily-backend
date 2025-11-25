package com.example.ApuDaily.publication.media.controller;

import com.example.ApuDaily.publication.media.dto.MediaResponseDto;
import com.example.ApuDaily.publication.media.model.Media;
import com.example.ApuDaily.publication.media.service.MediaService;
import java.io.IOException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${api.basePath}/${api.version}")
public class MediaController {

  @Autowired MediaService mediaService;

  @Autowired ModelMapper modelMapper;

  @PostMapping("/upload")
  public ResponseEntity<MediaResponseDto> upload(@RequestParam("file") MultipartFile file)
      throws IOException {
    Media media = mediaService.store(file);
    return ResponseEntity.ok(modelMapper.map(media, MediaResponseDto.class));
  }
}
