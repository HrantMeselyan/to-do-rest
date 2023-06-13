package com.example.todo.service;

import com.example.todo.dto.CategoryRequestDto;
import com.example.todo.dto.CategoryResponseDto;

public interface CategoryService {
    CategoryResponseDto save(CategoryRequestDto categoryRequestDto);
}
