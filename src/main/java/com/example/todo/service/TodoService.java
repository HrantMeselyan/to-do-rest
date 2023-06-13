package com.example.todo.service;

import com.example.todo.dto.TodoRequestDto;
import com.example.todo.dto.TodoResponseDto;
import com.example.todo.entity.User;

import java.util.List;

public interface TodoService {
    TodoResponseDto create(TodoRequestDto todoRequestDto, User user);

    List<TodoResponseDto> getAll(int id);
}
