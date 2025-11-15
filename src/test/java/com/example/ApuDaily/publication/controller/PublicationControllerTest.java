package com.example.ApuDaily.publication.controller;

import com.example.ApuDaily.publication.category.model.Category;
import com.example.ApuDaily.publication.media.model.Media;
import com.example.ApuDaily.publication.post.dto.PostCreateRequestDto;
import com.example.ApuDaily.publication.post.dto.PostResponseDto;
import com.example.ApuDaily.publication.post.model.Post;
import com.example.ApuDaily.publication.tag.model.Tag;
import com.example.ApuDaily.testconfig.BeanConfig;
import com.example.ApuDaily.testconfig.ContainerConfig;
import com.example.ApuDaily.testutil.DbUtil;
import com.example.ApuDaily.testutil.DtoUtil;
import com.example.ApuDaily.testutil.URL;
import com.example.ApuDaily.user.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Import(BeanConfig.class)
public class PublicationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    DbUtil dbUtil;

    @Autowired
    DtoUtil dtoUtil;

    @Container
    static PostgreSQLContainer<?> postgresqlContainer =
            new PostgreSQLContainer<>(DockerImageName.parse(ContainerConfig.POSTGRES));

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @BeforeEach
     void beforeEach() {
        dbUtil.deleteAll();
    }

    @Test
    @WithMockUser(username = "Username-1", password = "Password-1", roles = "USER")
    void createPost_shouldReturnCreatedDtoWith201_whenValidFields() throws Exception {
        //Given
        User user = dbUtil.createUser(1);
        Category category = dbUtil.createCategory(1);
        Tag tag = dbUtil.createTag(1);
        Media thumbnail = dbUtil.createMedia(1);

        PostCreateRequestDto requestDto = dtoUtil.postCreateRequestDto(
                1, category.getId(), List.of(tag.getId()), thumbnail.getId(), user);

        // When
        MvcResult mvcResult = mockMvc.perform(post(URL.POSTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))

                // Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title", is(requestDto.getTitle())))
                .andExpect(jsonPath("$.description", is(requestDto.getDescription())))
                .andExpect(jsonPath("$.content", is(requestDto.getContent())))
                .andDo(print())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        PostResponseDto responseDto =
                objectMapper.readValue(responseContent, new TypeReference<PostResponseDto>() {});

        //Db
        Post createdPost = dbUtil.getPostById(responseDto.getId());
        assertEquals(user.getId(), createdPost.getUser().getId());
    }
}
