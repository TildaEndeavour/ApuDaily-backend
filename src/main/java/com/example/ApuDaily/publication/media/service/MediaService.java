package com.example.ApuDaily.publication.media.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MediaService {
    String store(MultipartFile file) throws IOException;
}
