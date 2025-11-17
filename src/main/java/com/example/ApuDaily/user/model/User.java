package com.example.ApuDaily.user.model;

import com.example.ApuDaily.publication.comment.model.Comment;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.reaction.model.Reaction;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Schema(description = "User data model")
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Unique user identifier")
  private Long id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false, unique = false)
  private String password;

  @Schema(description = "Account creation time")
  @Column(name = "created_at", nullable = false, unique = false)
  private LocalDateTime createdAt;

  @Schema(description = "Time of last account update")
  @Column(name = "updated_at", nullable = true, unique = false)
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

  public List<Post> getPostsSortedById() {
    return this.getPosts().stream().sorted(Comparator.comparingLong(Post::getId)).toList();
  }

  public List<Comment> getCommentariesSortedById() {
    return this.getComments().stream().sorted(Comparator.comparingLong(Comment::getId)).toList();
  }

  public List<Reaction> getReactionsSortedById() {
    return this.getReactions().stream().sorted(Comparator.comparingLong(Reaction::getId)).toList();
  }
}
