package com.example.ApuDaily.config;

import com.example.ApuDaily.publication.comment.dto.CommentResponseDto;
import com.example.ApuDaily.publication.comment.model.Comment;
import com.example.ApuDaily.publication.reaction.dto.ReactionResponseDto;
import com.example.ApuDaily.publication.reaction.model.Reaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
  @Bean
  public ModelMapper modelMapper() {

    ModelMapper modelMapper = new ModelMapper();

    TypeMap<Comment, CommentResponseDto> commentTypeMap =
        modelMapper.createTypeMap(Comment.class, CommentResponseDto.class);
    commentTypeMap.addMappings(
        mapper -> {
          mapper.map(src -> src.getPost().getId(), CommentResponseDto::setPostId);
          mapper.map(src -> src.getParentComment().getId(), CommentResponseDto::setParentCommentId);
        });

    TypeMap<Reaction, ReactionResponseDto> reactionTypeMap =
        modelMapper.createTypeMap(Reaction.class, ReactionResponseDto.class);
    reactionTypeMap.addMappings(
        mapper -> {
          mapper.map(src -> src.getTargetType().getId(), ReactionResponseDto::setTargetTypeId);
        });

    return modelMapper;
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
    return objectMapper;
  }
}
