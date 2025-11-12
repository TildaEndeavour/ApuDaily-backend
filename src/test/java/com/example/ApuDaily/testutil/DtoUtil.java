package com.example.ApuDaily.testutil;

import com.example.ApuDaily.publication.comment.dto.CommentCreateRequestDto;
import com.example.ApuDaily.publication.comment.dto.CommentDeleteRequestDto;
import com.example.ApuDaily.publication.comment.dto.CommentUpdateRequestDto;

import java.util.Random;

public class DtoUtil {
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
