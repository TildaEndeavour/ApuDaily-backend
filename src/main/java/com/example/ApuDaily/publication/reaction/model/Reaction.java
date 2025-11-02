package com.example.ApuDaily.publication.reaction.model;

import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.user.model.User;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "react_id")
    private ReactionType reactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
