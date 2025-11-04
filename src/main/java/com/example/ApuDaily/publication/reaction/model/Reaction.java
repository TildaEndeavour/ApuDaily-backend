package com.example.ApuDaily.publication.reaction.model;

import com.example.ApuDaily.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reactions")
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="target_type_id")
    private TargetType targetType;

    @Column(name = "entity_id", nullable = false, unique = false)
    private long entityId;

    @Column(name = "isUpvote", nullable = false, unique = false)
    private Boolean isUpvote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at", nullable = false, unique = false)
    private LocalDateTime createdAt;
}
