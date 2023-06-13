package com.example.todo.endpoint;

import com.example.todo.dto.CategoryDto;
import com.example.todo.dto.CategoryRequestDto;
import com.example.todo.dto.CategoryResponseDto;
import com.example.todo.entity.Category;
import com.example.todo.mapper.CategoryMapper;
import com.example.todo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryEndpoint {
    public final CategoryRepository categoryRepository;
    public final CategoryMapper categoryMapper;

    @PostMapping()
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        Category savedCategory = categoryRepository.save(categoryMapper.map(categoryRequestDto));
        return ResponseEntity.ok(categoryMapper.map(savedCategory));
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        List<Category> all = categoryRepository.findAll();
        return ResponseEntity.ok(categoryMapper.mapToList(all));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") int id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
