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

    @Value("${api.basePath}/${api.version}/users/auth/**")
    private String authUrl;

    @Value("${api.basePath}/${api.version}/users")
    private String signupUrl;

    @Value("${api.basePath}/${api.version}/upload")
    private String uploadUrl;

    @Value("/uploads/**")
    private String mediaUrl;

    @Value("${api.basePath}/${api.version}/posts/*")
    private String postsUrl;

    @Value("${api.basePath}/${api.version}/categories")
    private String categoriesUrl;

    @Value("${api.basePath}/${api.version}/tags")
    private String tagsUrl;
}
