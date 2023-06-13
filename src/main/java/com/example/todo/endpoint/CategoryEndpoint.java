package com.example.todo.endpoint;

import com.example.todo.dto.CategoryRequestDto;
import com.example.todo.dto.CategoryResponseDto;
import com.example.todo.entity.Category;
import com.example.todo.mapper.CategoryMapper;
import com.example.todo.repository.CategoryRepository;
import com.example.todo.service.CategoryService;
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
    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        return ResponseEntity.ok(categoryService.save(categoryRequestDto));
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") int id) {
        if (categoryService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
