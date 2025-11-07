package com.example.ApuDaily.publication.post.repository;

import com.example.ApuDaily.publication.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    @Modifying
    @Query("UPDATE Post p SET p.commentariesCount = p.commentariesCount + 1 WHERE p.id = :id")
    void incrementCommentCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.commentariesCount = p.commentariesCount - 1 WHERE p.id = :id")
    void decrementCommentCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.upvotesCount = p.upvotesCount + 1 WHERE p.id = :id")
    void incrementUpvoteCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.upvotesCount = p.upvotesCount - 1 WHERE p.id = :id")
    void decrementUpvoteCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.downvotesCount = p.downvotesCount + 1 WHERE p.id = :id")
    void incrementDownvoteCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.downvotesCount = p.downvotesCount - 1 WHERE p.id = :id")
    void decrementDownvoteCount(@Param("id") Long id);
}
