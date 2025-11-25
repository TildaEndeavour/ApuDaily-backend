package com.example.ApuDaily.publication.comment.repository;

import com.example.ApuDaily.publication.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository
    extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
  @Modifying
  @Query("UPDATE Comment c SET c.upvotesCount = c.upvotesCount + 1 WHERE c.id = :id")
  void incrementUpvoteCount(@Param("id") Long id);

  @Modifying
  @Query("UPDATE Comment c SET c.downvotesCount = c.upvotesCount - 1 WHERE c.id = :id")
  void decrementUpvoteCount(@Param("id") Long id);

  @Modifying
  @Query("UPDATE Comment c SET c.downvotesCount = c.downvotesCount + 1 WHERE c.id = :id")
  void incrementDownvoteCount(@Param("id") Long id);

  @Modifying
  @Query("UPDATE Comment c SET c.downvotesCount = c.downvotesCount - 1 WHERE c.id = :id")
  void decrementDownvoteCount(@Param("id") Long id);
}
