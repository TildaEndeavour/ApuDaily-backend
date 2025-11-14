package com.example.ApuDaily.testutil;

import com.example.ApuDaily.publication.category.model.Category;
import com.example.ApuDaily.publication.category.repository.CategoryRepository;
import com.example.ApuDaily.publication.comment.model.Comment;
import com.example.ApuDaily.publication.comment.repository.CommentRepository;
import com.example.ApuDaily.publication.media.model.Media;
import com.example.ApuDaily.publication.media.repository.MediaRepository;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.post.repository.PostRepository;
import com.example.ApuDaily.publication.reaction.model.Reaction;
import com.example.ApuDaily.publication.reaction.model.TargetType;
import com.example.ApuDaily.publication.reaction.repository.ReactionRepository;
import com.example.ApuDaily.publication.reaction.repository.TargetTypeRepository;
import com.example.ApuDaily.publication.tag.model.Tag;
import com.example.ApuDaily.publication.tag.repository.TagRepository;
import com.example.ApuDaily.user.model.Role;
import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.repository.RoleRepository;
import com.example.ApuDaily.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZonedDateTime;
import java.util.List;

@TestComponent
public class DbUtil implements Util {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    TargetTypeRepository targetTypeRepository;

    @Autowired
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional
    public void deleteAll() {
        userRepository.deleteAll();
        postRepository.deleteAll();
        categoryRepository.deleteAll();
        tagRepository.deleteAll();
        commentRepository.deleteAll();
        mediaRepository.deleteAll();
        reactionRepository.deleteAll();
        targetTypeRepository.deleteAll();
    }

    @Override
    public User createUser(int seed){
        return userRepository.save(User.builder()
                .username("User " + seed)
                .email("email" + seed + "@gmail.com")
                .password("password " + seed)
                .createdAt(ZonedDateTime.parse("2024-01-01T12:00:00Z").toLocalDateTime())
                .updatedAt(null)
                .build());
    }

    @Override
    public Role createUserRole (){
        return roleRepository.save(Role.builder()
                .name("ROLE_USER")
                .build());
    }

    @Override
    public Role createAdminRole (){
        return roleRepository.save(Role.builder()
                .name("ROLE_ADMIN")
                .build());
    }

    @Override
    public Media createMedia(int seed){
        String filename = "filename " + seed;
        String extension = ".jpg";
        String url = "/uploads/" + filename + extension;

        return mediaRepository.save(Media.builder()
                .postId(null)
                .statusId((short) 1)
                .filename("filename " + seed)
                .extension("jpg")
                .url(url)
                .createdAt(ZonedDateTime.parse("2024-01-01T12:00:00Z").toLocalDateTime())
                .build());
    }

    @Override
    public Category createCategory(int seed){
        return categoryRepository.save(Category.builder()
                .name("Category " + seed)
                .slug("category " + seed)
                .build());
    }

    @Override
    public Tag createTag(int seed){
        return tagRepository.save(Tag.builder()
                .name("Tag " + seed)
                .build());
    }

    @Override
    public Post createPost(int seed){
        User user = createUser(seed);
        Media thumbnail = createMedia(seed);
        Category category = createCategory(seed);
        Tag tag = createTag(seed);

        return postRepository.save(Post.builder()
                .user(user)
                .title("Title " + seed)
                .description("Description " + seed)
                .thumbnail(thumbnail)
                .content("Publication content " + seed)
                .category(category)
                .tags(List.of(tag))
                .commentariesCount(0)
                .upvotesCount(0)
                .downvotesCount(0)
                .viewCount(0)
                .createdAt(ZonedDateTime.parse("2024-01-01T12:00:00Z").toLocalDateTime())
                .updatedAt(null)
                .build());
    }

    @Override
    public Post createPost(int seed, User user){
        Media thumbnail = createMedia(seed);
        Category category = createCategory(seed);
        Tag tag = createTag(seed);

        return postRepository.save(Post.builder()
                .user(user)
                .title("Title " + seed)
                .description("Description " + seed)
                .thumbnail(thumbnail)
                .content("Publication content " + seed)
                .category(category)
                .tags(List.of(tag))
                .commentariesCount(0)
                .upvotesCount(0)
                .downvotesCount(0)
                .viewCount(0)
                .createdAt(ZonedDateTime.parse("2024-01-01T12:00:00Z").toLocalDateTime())
                .updatedAt(null)
                .build());
    }

    @Override
    public Comment createComment(int seed, Post post, User user){
        return commentRepository.save(Comment.builder()
                .user(user)
                .post(post)
                .parentComment(null)
                .content("Commentary content " + seed)
                .createdAt(ZonedDateTime.parse("2024-01-01T12:00:00Z").toLocalDateTime())
                .updatedAt(null)
                .build());
    }

    @Override
    public Comment createComment(int seed, Post post){
        User user = createUser(seed);
        return commentRepository.save(Comment.builder()
                .user(user)
                .post(post)
                .parentComment(null)
                .content("Commentary content " + seed)
                .createdAt(ZonedDateTime.parse("2024-01-01T12:00:00Z").toLocalDateTime())
                .updatedAt(null)
                .build());
    }

    public TargetType createPostTargetType(){
        return targetTypeRepository.save(TargetType.builder()
                .name("POST")
                .build());
    }

    @Override
    public Reaction createReaction(int seed, Post post, User user){
        TargetType postTarget = createPostTargetType();
        return reactionRepository.save(Reaction.builder()
                .user(user)
                .targetType(postTarget)
                .entityId(post.getId())
                .isUpvote((seed & 1) == 1)
                .createdAt(ZonedDateTime.parse("2024-01-01T12:00:00Z").toLocalDateTime())
                .build());
    }

    @Override
    public Reaction createReaction(int seed, Post post){
        User user = createUser(seed);
        TargetType postTarget = createPostTargetType();
        return reactionRepository.save(Reaction.builder()
                .user(user)
                .targetType(postTarget)
                .entityId(post.getId())
                .isUpvote((seed & 1) == 1)
                .createdAt(ZonedDateTime.parse("2024-01-01T12:00:00Z").toLocalDateTime())
                .build());
    }
}
