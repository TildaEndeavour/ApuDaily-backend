package com.example.ApuDaily.publication.post.specification;

import com.example.ApuDaily.publication.post.dto.PostSearchRequestDto;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.tag.model.Tag;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PostSpecification {
    public static Specification<Post> withFilters(PostSearchRequestDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getSearchQuery() != null && !filter.getSearchQuery().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + filter.getSearchQuery().toLowerCase() + "%"));
            }

            if (filter.getUsersId() != null && !filter.getUsersId().isEmpty()) {
                predicates.add(root.get("user").get("id").in(filter.getUsersId()));
            }

            if (filter.getTagsId() != null && !filter.getTagsId().isEmpty()) {
                Join<Post, Tag> tagsJoin = root.join("tags");
                predicates.add(tagsJoin.get("id").in(filter.getTagsId()));
            }

            if (filter.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), filter.getCategoryId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
