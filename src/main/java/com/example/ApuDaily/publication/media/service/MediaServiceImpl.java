package com.example.ApuDaily.publication.media.service;

import com.example.ApuDaily.publication.media.model.Media;
import com.example.ApuDaily.publication.media.repository.MediaRepository;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    MediaRepository mediaRepository;

    private final Tika tika = new Tika();

    private final Path uploadDir;

    @Value("${allowed-filetypes}")
    private List<String> allowedTypes;

    public MediaServiceImpl(@Value("${spring.servlet.multipart.location}") String uploadDir) throws IOException {
        this.uploadDir = Path.of(uploadDir);
        Files.createDirectories(this.uploadDir);
    }

    @Override
    public String store(MultipartFile file) throws IOException {

        // Check supported extensions
        if(!isValidFileType(file)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This file extension isn't supported");

        // Check if file is empty
        if(file.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty file");

        String filename = UUID.randomUUID().toString();
        String ext = getExtension(file.getOriginalFilename());

        Path path = uploadDir.resolve(filename + ext);

        try (var in = file.getInputStream()) {
            Files.copy(in, path);
        }

        String resultUrl = "/uploads/" + filename + ext;

        Media media = Media.builder()
                .statusId((short) 1)
                .filename(filename)
                .extension(ext)
                .url(resultUrl)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        mediaRepository.save(media);

        return resultUrl;
    }

    private boolean isValidFileType(MultipartFile file) throws IOException{
        String contentType = file.getContentType();
        String mimeType = tika.detect(file.getInputStream());

        return contentType != null
                && allowedTypes.contains(mimeType)
                && allowedTypes.contains(contentType);
    }

    private String getExtension(String original) {
        if (original == null) return "";
        String clean = Paths.get(original).getFileName().toString();
        int dot = clean.lastIndexOf('.');
        return (dot != -1 && dot < clean.length() - 1) ? clean.substring(dot).toLowerCase() : "";
    }
}
