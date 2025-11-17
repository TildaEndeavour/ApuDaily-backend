package com.example.ApuDaily.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class ApiUrl {
    @Value("/error/**")
    private String errorUrl;

    @Value("/swagger-ui/**")
    private String swaggerUiUrl;

    @Value("/v3/api-docs/**")
    private String swaggerEndpointsUrl;

    @Value("${api.basePath}/${api.version}/users/auth/**")
    private String authUrl;

    @Value("${api.basePath}/${api.version}/users")
    private String signupUrl;

    @Value("${api.basePath}/${api.version}/users/profiles")
    private String usersProfilesUrl;

    @Value("${api.basePath}/${api.version}/users/profiles/*")
    private String usersProfileUrl;

    @Value("${api.basePath}/${api.version}/users/timezones")
    private String timezoneUrl;

    @Value("${api.basePath}/${api.version}/upload")
    private String uploadUrl;

    @Value("/uploads/**")
    private String mediaUrl;

    @Value("${api.basePath}/${api.version}/posts")
    private String postsUrl;

    @Value("${api.basePath}/${api.version}/posts/search")
    private String postsSearchUrl;

    @Value("${api.basePath}/${api.version}/posts/*")
    private String postDetailsUrl;

    @Value("${api.basePath}/${api.version}/commentaries/search")
    private String commentariesSearchUrl;

    @Value("${api.basePath}/${api.version}/categories")
    private String categoriesUrl;

    @Value("${api.basePath}/${api.version}/tags")
    private String tagsUrl;
}
