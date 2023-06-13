package com.example.todo.endpoint;

import com.example.todo.dto.TodoDto;
import com.example.todo.dto.TodoRequestDto;
import com.example.todo.dto.TodoResponseDto;
import com.example.todo.entity.Status;
import com.example.todo.entity.Todo;
import com.example.todo.mapper.TodoMapper;
import com.example.todo.repository.TodoRepository;
import com.example.todo.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class ToDoEndpoint {
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @PostMapping()
    public ResponseEntity<TodoResponseDto> create(@RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal CurrentUser currentUser) {
        todoRequestDto.setUser(currentUser.getUser());
        todoRequestDto.setStatus(Status.NOT_STARTED);
        Todo save = todoRepository.save(todoMapper.map(todoRequestDto));
        return ResponseEntity.ok(todoMapper.map(save));
    }

    @GetMapping()
    public ResponseEntity<List<TodoResponseDto>> getAll(@AuthenticationPrincipal CurrentUser currentUser) {
        List<Todo> allByUserId = todoRepository.getAllByUser_Id(currentUser.getUser().getId());
        if (allByUserId.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(todoMapper.mapList(allByUserId));
    }

    @GetMapping("/byStatus")
    public ResponseEntity<List<TodoResponseDto>> getByStatus(@RequestParam String status, @AuthenticationPrincipal CurrentUser currentUser) {
        List<Todo> allByUserIdAndStatus = todoRepository.findAllByUserIdAndStatus(currentUser.getUser().getId(), status);
        if (allByUserIdAndStatus.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(todoMapper.mapList(allByUserIdAndStatus));
    }

    @GetMapping("/byCategory")
    public ResponseEntity<List<TodoResponseDto>> getByCategory(@RequestParam int categoryId, @AuthenticationPrincipal CurrentUser currentUser) {
        List<Todo> allByCategoryIdAndUserId = todoRepository.findAllByCategoryIdAndUserId(categoryId, currentUser.getUser().getId());
        if (allByCategoryIdAndUserId.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(todoMapper.mapList(allByCategoryIdAndUserId));
    }

    @PutMapping("/id")
    public ResponseEntity<TodoDto> update(@RequestBody TodoDto todoDto, @AuthenticationPrincipal CurrentUser currentUser) {
        Todo save = todoRepository.save(todoMapper.map(todoDto));
        return ResponseEntity.ok(todoMapper.mapToDto(save));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, @AuthenticationPrincipal CurrentUser currentUser) {
        Todo todo = todoRepository.getById(id);
        if (currentUser.getUser().getId() == todo.getId()) {
            todoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}