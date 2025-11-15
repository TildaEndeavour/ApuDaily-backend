package com.example.ApuDaily.publication.repository;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.post.repository.PostRepository;
import com.example.ApuDaily.testconfig.BeanConfig;
import com.example.ApuDaily.testconfig.ContainerConfig;
import com.example.ApuDaily.testutil.DbUtil;
import com.example.ApuDaily.user.model.Role;
import com.example.ApuDaily.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Testcontainers
@Import(BeanConfig.class)
public class PublicationRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgresqlContainer =
            new PostgreSQLContainer<>(DockerImageName.parse(ContainerConfig.POSTGRES));

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @Autowired
    PostRepository postRepository;

    @Autowired
    DbUtil dbUtil;

    @BeforeEach
    void beforeEach() {
        dbUtil.deleteAll();
    }

    @Test
    void findUserPost_shouldReturnPost() {
        //Given
        Role userRole = dbUtil.createUserRole();
        User author = dbUtil.createUser(1, userRole);
        User anotherUser1 = dbUtil.createUser(2, userRole);
        User anotherUser2 = dbUtil.createUser(3, userRole);

        Post actualPost = dbUtil.createPost(1, author);
        Post anotherUser1Post = dbUtil.createPost(2, anotherUser1);
        Post anotherUser2Post = dbUtil.createPost(3, anotherUser2);

        Specification<Post> spec = (root, query, cb) ->
                cb.equal(root.get("user").get("id"), author.getId());

        int currentPageNumber = 0;
        int pageSize = 10;

        //When
        Page<Post> result = postRepository.findAll(spec, PageRequest.of(currentPageNumber, pageSize));

        //Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(actualPost.getId(), result.getContent().get(0).getId());
    }
}
