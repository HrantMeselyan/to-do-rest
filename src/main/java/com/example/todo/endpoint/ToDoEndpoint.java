package com.example.todo.endpoint;

import com.example.todo.dto.TodoDto;
import com.example.todo.dto.TodoRequestDto;
import com.example.todo.dto.TodoResponseDto;
import com.example.todo.entity.Todo;
import com.example.todo.mapper.TodoMapper;
import com.example.todo.repository.TodoRepository;
import com.example.todo.security.CurrentUser;
import com.example.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class ToDoEndpoint {
    private final TodoMapper todoMapper;
    private final TodoService todoService;

    @PostMapping()
    public ResponseEntity<TodoResponseDto> create(@RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(todoService.create(todoRequestDto, currentUser.getUser()));
    }

    @GetMapping()
    public ResponseEntity<List<TodoResponseDto>> getAll(@AuthenticationPrincipal CurrentUser currentUser) {
        if (todoService.getAll(currentUser.getUser().getId()) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(todoService.getAll(currentUser.getUser().getId()));
    }

    @GetMapping("/byStatus")
    public ResponseEntity<List<TodoResponseDto>> getByStatus(@RequestParam String status, @AuthenticationPrincipal CurrentUser currentUser) {
        List<Todo> allByUserIdAndStatus = todoService.findAllByUserIdAndStatus(currentUser.getUser().getId(), status);
        if (allByUserIdAndStatus.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(todoMapper.mapList(allByUserIdAndStatus));
    }

    @GetMapping("/byCategory")
    public ResponseEntity<List<TodoResponseDto>> getByCategory(@RequestParam int categoryId, @AuthenticationPrincipal CurrentUser currentUser) {
        List<Todo> allByCategoryIdAndUserId = todoService.findAllByCategoryIdAndUserId(categoryId, currentUser.getUser().getId());
        if (allByCategoryIdAndUserId.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(todoMapper.mapList(allByCategoryIdAndUserId));
    }

    @PutMapping("/id")
    public ResponseEntity<TodoDto> update(@RequestBody TodoDto todoDto, @AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(todoService.save(todoDto, currentUser.getUser().getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, @AuthenticationPrincipal CurrentUser currentUser) {
        if (todoService.delete(id, currentUser.getUser().getId())) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
