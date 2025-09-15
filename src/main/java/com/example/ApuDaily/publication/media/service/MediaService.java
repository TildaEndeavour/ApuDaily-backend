package com.example.ApuDaily.publication.media.service;

import com.example.ApuDaily.publication.media.model.Media;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MediaService {
    Media store(MultipartFile file) throws IOException;
}
