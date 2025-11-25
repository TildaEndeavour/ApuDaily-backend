package com.example.ApuDaily.publication.category.controller;

import com.example.ApuDaily.publication.category.dto.CategoryResponseDto;
import com.example.ApuDaily.publication.category.model.Category;
import com.example.ApuDaily.publication.category.repository.CategoryRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.basePath}/${api.version}")
public class CategoryController {

  @Autowired CategoryRepository categoryRepository;

  @Autowired ModelMapper modelMapper;

  @GetMapping("/categories")
  public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
    List<Category> categories = categoryRepository.findAll();
    List<CategoryResponseDto> dtoList =
        categories.stream()
            .map(category -> modelMapper.map(category, CategoryResponseDto.class))
            .toList();
    return ResponseEntity.ok(dtoList);
  }
}
