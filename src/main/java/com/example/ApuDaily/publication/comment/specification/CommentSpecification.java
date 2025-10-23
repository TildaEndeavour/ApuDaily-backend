package com.example.ApuDaily.publication.comment.specification;

import com.example.ApuDaily.publication.comment.dto.CommentFilter;
import com.example.ApuDaily.publication.comment.model.Comment;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CommentSpecification {
    public static Specification<Comment> withFilters(CommentFilter filter){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filter.getUserId() != null){
                predicates.add(cb.equal(root.get("user").get("id"), filter.getUserId()));
            }

            if(filter.getPostId() != null){
                predicates.add(cb.equal(root.get("post").get("id"), filter.getPostId()));
            }

            if(filter.getParentCommentId() != null){
                predicates.add(cb.equal(root.get("parentComment").get("id"), filter.getParentCommentId()));
            } else {
                predicates.add(cb.isNull(root.get("parentComment")));
            }

            if(filter.getCommentId() != null){
                predicates.add(cb.equal(root.get("id"), filter.getCommentId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
