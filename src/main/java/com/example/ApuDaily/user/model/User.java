package com.example.ApuDaily.user.model;

import com.example.ApuDaily.publication.comment.model.Comment;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.reaction.model.Reaction;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false, unique = false)
    private String password;

    @Column(name= "created_at", nullable = false, unique = false)
    private LocalDateTime createdAt;

    @Column(name= "updated_at", nullable = true, unique = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id") // FOREIGN KEY(role_id) REFERENCES roles(id)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @ManyToMany(mappedBy = "user")
    private List<Reaction> reactions;

    public List<Post> getPostsSortedById(){
        return this.getPosts().stream()
                .sorted(Comparator.comparingLong(Post::getId))
                .toList();
    }

    public List<Comment> getCommentariesSortedById(){
        return this.getComments().stream()
                .sorted(Comparator.comparingLong(Comment::getId))
                .toList();
    }

    public List<Reaction> getReactionsSortedById(){
        return this.getReactions().stream()
                .sorted(Comparator.comparingLong(Reaction::getId))
                .toList();
    }
}
