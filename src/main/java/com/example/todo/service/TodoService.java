package com.example.todo.service;

import com.example.todo.dto.TodoDto;
import com.example.todo.dto.TodoRequestDto;
import com.example.todo.dto.TodoResponseDto;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;

import java.util.List;

public interface TodoService {
    TodoResponseDto create(TodoRequestDto todoRequestDto, User user);

    List<TodoResponseDto> getAll(int id);

    List<Todo> findAllByCategoryIdAndUserId(int categoryId, int id);

    TodoDto save(TodoDto todoDto, int id);

    boolean delete(int id, int userId);
}
