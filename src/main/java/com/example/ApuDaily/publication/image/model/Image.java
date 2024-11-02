package com.example.ApuDaily.publication.image.model;

import com.example.ApuDaily.publication.post.content.model.ContentBlock;
import com.example.ApuDaily.publication.post.model.Post;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", unique = false, nullable = false)
    private String fileName;

    @Column(name = "file_type", unique = false, nullable = false)
    private String fileType;

    @Column(name = "image", unique = false, nullable = false)
    private Blob image;

    @Column(name = "download_url", unique = false, nullable = false)
    private String downloadUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_block_id")
    private ContentBlock contentBlock;
}
