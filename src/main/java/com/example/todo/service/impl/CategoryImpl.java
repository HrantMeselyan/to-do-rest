package com.example.todo.service.impl;

import com.example.todo.dto.CategoryRequestDto;
import com.example.todo.dto.CategoryResponseDto;
import com.example.todo.entity.Category;
import com.example.todo.mapper.CategoryMapper;
import com.example.todo.repository.CategoryRepository;
import com.example.todo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public CategoryResponseDto save(CategoryRequestDto categoryRequestDto) {
        Category save = categoryRepository.save(categoryMapper.map(categoryRequestDto));
        return categoryMapper.map(save);
    }
}
