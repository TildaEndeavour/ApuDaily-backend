package com.example.ApuDaily.config;

import com.example.ApuDaily.publication.comment.dto.CommentResponseDto;
import com.example.ApuDaily.publication.comment.model.Comment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public ModelMapper modelMapper(){

        ModelMapper modelMapper = new ModelMapper();

        TypeMap<Comment, CommentResponseDto> typeMap = modelMapper.createTypeMap(Comment.class, CommentResponseDto.class);
        typeMap.addMappings(mapper -> {
            mapper.map(src -> src.getPost().getId(), CommentResponseDto::setPostId);
        });

        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }
}
