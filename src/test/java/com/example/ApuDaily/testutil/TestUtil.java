package com.example.ApuDaily.testutil;

import com.example.ApuDaily.publication.category.model.Category;
import com.example.ApuDaily.publication.comment.model.Comment;
import com.example.ApuDaily.publication.media.model.Media;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.reaction.model.Reaction;
import com.example.ApuDaily.publication.reaction.model.TargetType;
import com.example.ApuDaily.publication.tag.model.Tag;
import com.example.ApuDaily.user.model.Role;
import com.example.ApuDaily.user.model.User;

import java.time.ZonedDateTime;
import java.util.List;

public class TestUtil implements Util{
    @Override
    public Post createPost (int seed){

        User user = createUser(seed);
        Media thumbnail = createMedia(seed);
        Category category = createCategory(seed);
        List<Tag> tags = List.of(createTag(seed));

        return Post.builder()
                .Id(((long) seed))
                .user(user)
                .thumbnail(thumbnail)
                .title("Title " + seed)
                .description("Description " + seed)
                .content("Content " + seed)
                .category(category)
                .tags(tags)
                .commentariesCount(0)
                .upvotesCount(0)
                .downvotesCount(0)
                .viewCount(0)
                .build();
    }

    @Override
    public Post createPost (int seed, User user){

        Media thumbnail = createMedia(seed);
        Category category = createCategory(seed);
        List<Tag> tags = List.of(createTag(seed));

        return Post.builder()
                .Id(((long) seed))
                .user(user)
                .thumbnail(thumbnail)
                .title("Title " + seed)
                .description("Description " + seed)
                .content("Content " + seed)
                .category(category)
                .tags(tags)
                .commentariesCount(0)
                .upvotesCount(0)
                .downvotesCount(0)
                .viewCount(0)
                .build();
    }

    @Override
    public Comment createComment (int seed, Post post){
        User user = createUser(seed);

        return Comment.builder()
                .id((long) seed)
                .post(post)
                .user(user)
                .parentComment(null)
                .content("Comment " + seed)
                .createdAt(ZonedDateTime.parse("2024-01-01T12:00:00Z").toLocalDateTime())
                .updatedAt(null)
                .build();
    }

    @Override
    public Comment createComment (int seed, Post post, User user){
        return Comment.builder()
                .id((long) seed)
                .post(post)
                .user(user)
                .parentComment(null)
                .content("Comment " + seed)
                .createdAt(ZonedDateTime.parse("2024-01-01T12:00:00Z").toLocalDateTime())
                .updatedAt(null)
                .build();
    }

    @Override
    public Reaction createReaction (int seed, Post post){
        User user = createUser(seed);

        TargetType target = TargetType.builder()
                .id((long) 1)
                .name("POST")
                .build();

        return Reaction.builder()
                .id((long) seed)
                .targetType(target)
                .entityId(post.getId())
                .isUpvote((seed & 1) == 1)
                .user(user)
                .createdAt(ZonedDateTime.parse("2024-01-01T12:00:00Z").toLocalDateTime())
                .build();
    }

    @Override
    public Reaction createReaction (int seed, Post post, User user){
        TargetType target = TargetType.builder()
                .id((long) 1)
                .name("POST")
                .build();

        return Reaction.builder()
                .id((long) seed)
                .targetType(target)
                .entityId(post.getId())
                .isUpvote((seed & 1) == 1)
                .user(user)
                .createdAt(ZonedDateTime.parse("2024-01-01T12:00:00Z").toLocalDateTime())
                .build();
    }

    @Override
    public Tag createTag (int seed){
        return Tag.builder()
                .id((long) seed)
                .name("Tag " + seed)
                .build();
    }

    @Override
    public Role createUserRole (){
        return Role.builder()
                .id((long) 1)
                .name("ROLE_USER")
                .build();
    }

    @Override
    public Role createAdminRole (){
        return Role.builder()
                .id((long) 2)
                .name("ROLE_ADMIN")
                .build();
    }

    public Category createCategory (int seed){
        return Category.builder()
                .id((long) seed)
                .name("Category " + seed)
                .slug("category " + seed)
                .build();
    }

    @Override
    public User createUser (int seed){
        return User.builder()
                .id((long) seed)
                .username("Username " + seed)
                .password("Password " + seed)
                .createdAt(ZonedDateTime.parse("2024-01-01T12:00:00Z").toLocalDateTime())
                .updatedAt(null)
                .build();
    }

    @Override
    public Media createMedia (int seed){
        String filename = "filename " + seed;
        String extension = ".jpg";
        String url = "/uploads/" + filename + extension;

        return Media.builder()
                .id((long) seed)
                .postId(null)
                .statusId((short) 1)
                .filename("filename " + seed)
                .extension("jpg")
                .url(url)
                .createdAt(ZonedDateTime.parse("2024-01-01T12:00:00Z").toLocalDateTime())
                .build();
    }

}
