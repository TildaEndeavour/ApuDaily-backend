package com.example.ApuDaily.publication.media.dto;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaResponseDto {
    private Long id;

    private Long postId;

    private Short statusId;

    private String filename;

    private String extension;

    private String url;

    private Timestamp createdAt;
}
