package com.example.ApuDaily.publication.media.controller;

import com.example.ApuDaily.publication.media.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("${api.basePath}/${api.version}")
public class MediaController {

    @Autowired
    MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) throws IOException{
        try{
            String path = mediaService.store(file);
            Map<String, String> response = new HashMap<>();
            response.put("path", path);
            return ResponseEntity.ok(response);
        }
        catch(ResponseStatusException e){
            Map<String, String> error = new HashMap<>();
            error.put("status", e.getStatusCode().toString());
            error.put("error", e.getReason());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
