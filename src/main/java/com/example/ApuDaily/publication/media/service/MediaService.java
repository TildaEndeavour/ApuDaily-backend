package com.example.ApuDaily.publication.media.service;

import com.example.ApuDaily.publication.media.model.Media;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
  Media store(MultipartFile file) throws IOException;
}
