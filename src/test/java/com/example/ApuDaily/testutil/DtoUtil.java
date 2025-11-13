package com.example.ApuDaily.testutil;

import com.example.ApuDaily.publication.category.model.Category;
import com.example.ApuDaily.publication.comment.dto.CommentCreateRequestDto;
import com.example.ApuDaily.publication.comment.dto.CommentDeleteRequestDto;
import com.example.ApuDaily.publication.comment.dto.CommentUpdateRequestDto;
import com.example.ApuDaily.publication.media.model.Media;
import com.example.ApuDaily.publication.post.dto.PostCreateRequestDto;
import com.example.ApuDaily.publication.tag.model.Tag;
import com.example.ApuDaily.user.model.User;

import java.util.List;
import java.util.Random;

public class DtoUtil {

    public PostCreateRequestDto postCreateRequestDto (
            int seed, Long categoryId, List<Long> tagsId, Long thumbnailId, User user){
        return PostCreateRequestDto.builder()
                .authorId(user.getId())
                .thumbnailId(thumbnailId)
                .title("Publication title " + seed)
                .description("Publication description " + seed)
                .content("Publication content " + seed)
                .categoryId(categoryId)
                .tagsId(tagsId)
                .build();
    }

    public CommentCreateRequestDto commentCreateRequestDto (int seed){
        Random random = new Random(seed);

        return CommentCreateRequestDto.builder()
                .postId(random.nextLong())
                .parentCommentId(random.nextLong())
                .content("Comment content " + seed)
                .build();
    }

    public CommentUpdateRequestDto commentUpdateRequestDto (int seed){
        Random random = new Random(seed);

        return CommentUpdateRequestDto.builder()
                .commentId(random.nextLong())
                .content("Comment content " + seed)
                .build();
    }

    public CommentDeleteRequestDto commentDeleteRequestDto (int seed){
        Random random = new Random(seed);

        return CommentDeleteRequestDto.builder()
                .commentId(random.nextLong())
                .build();
    }
}
