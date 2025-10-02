package com.example.ApuDaily.publication.tag.service;

import com.example.ApuDaily.publication.tag.dto.TagResponseDto;
import com.example.ApuDaily.publication.tag.model.Tag;
import com.example.ApuDaily.publication.tag.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService{

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<TagResponseDto> createTags(List<String> names) {
        return names.stream()
                .map(name -> tagRepository.findByName(name)
                        .orElseGet(() -> tagRepository.save(Tag.builder().name(name).build())))
                .map(tag -> modelMapper.map(tag, TagResponseDto.class))
                .toList();
    }

    @Override
    public List<TagResponseDto> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tag -> modelMapper.map(tag, TagResponseDto.class))
                .toList();
    }
}
