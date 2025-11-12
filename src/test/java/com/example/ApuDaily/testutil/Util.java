package com.example.ApuDaily.testutil;

import com.example.ApuDaily.publication.category.model.Category;
import com.example.ApuDaily.publication.comment.model.Comment;
import com.example.ApuDaily.publication.media.model.Media;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.reaction.model.Reaction;
import com.example.ApuDaily.publication.tag.model.Tag;
import com.example.ApuDaily.user.model.Role;
import com.example.ApuDaily.user.model.User;

public interface Util {
    Post createPost(int seed);

    Post createPost(int seed, User user);

    Media createMedia(int seed);

    Comment createComment(int seed);

    Comment createComment(int seed, User user);

    Category createCategory (int seed);

    Category createCategory (int seed, User user);

    Tag createTag(int seed);

    Tag createTag(int seed, User user);

    Reaction createReaction(int seed);

    Reaction createReaction(int seed, User user);

    User createUser(int seed);

    Role createUserRole();

    Role createAdminRole();
}
