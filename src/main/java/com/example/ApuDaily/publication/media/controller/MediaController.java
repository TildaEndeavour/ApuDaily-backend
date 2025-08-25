package com.example.ApuDaily.publication.media.controller;

import com.example.ApuDaily.publication.media.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("${api.basePath}/${api.version}")
public class MediaController {

    @Autowired
    MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException{
        String path = mediaService.store(file);
        return ResponseEntity.ok().body(path);
    }
}
