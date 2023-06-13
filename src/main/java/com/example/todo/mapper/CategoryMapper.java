package com.example.todo.mapper;

import com.example.todo.dto.CategoryRequestDto;
import com.example.todo.dto.CategoryResponseDto;
import com.example.todo.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category map(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto map(Category category);

    List<CategoryResponseDto> mapToList(List<Category> categoryList);
}
