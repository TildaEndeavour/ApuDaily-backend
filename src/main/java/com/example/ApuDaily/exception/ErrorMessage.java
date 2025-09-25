package com.example.ApuDaily.exception;

public enum ErrorMessage {
    AUTHENTICATION_ERROR("Authentication error"),
    ROLE_NOT_FOUND("Role not found"),
    ALREADY_EXISTS("Already exists"),
    INVALID_TOKEN("Invalid token"),
    USER_NOT_FOUND("User with id %d not found"),
    USER_POST_MISMATCH("Post with id %d doesn't belong to the user"),
    POST_NOT_FOUND("Post with id %d not found"),
    MEDIA_NOT_FOUND("Media with id %d not found"),
    CATEGORY_NOT_FOUND("Category with id %d not found"),
    NOT_FOUND("Not found");

    private final String name;

    ErrorMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
