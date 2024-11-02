package com.example.ApuDaily.publication.post.content.model;

import com.example.ApuDaily.publication.post.content.model.ContentBlock;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "content_type")
public class ContentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "contentType")
    private List<ContentBlock> contentBlockList;
}
