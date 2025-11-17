package com.example.ApuDaily.publication.comment.model;

import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.user.model.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts_commentaries")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_comment_id")
  private Comment parentComment;

  @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> replies;

  @Column(name = "content", unique = false, nullable = false)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @Column(name = "upvotes_count", nullable = false, unique = false)
  private int upvotesCount;

  @Column(name = "downvotes_count", nullable = false, unique = false)
  private int downvotesCount;

  @Column(name = "created_at", unique = false, nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", unique = false, nullable = false)
  private LocalDateTime updatedAt;
}
