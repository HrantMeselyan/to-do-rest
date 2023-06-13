package com.example.todo.service;

import com.example.todo.dto.CategoryRequestDto;
import com.example.todo.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto save(CategoryRequestDto categoryRequestDto);

    List<CategoryResponseDto> findAll();
    Boolean delete(int id);
}
