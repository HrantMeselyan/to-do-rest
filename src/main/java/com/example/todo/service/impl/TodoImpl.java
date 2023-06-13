package com.example.todo.service.impl;

import com.example.todo.dto.TodoRequestDto;
import com.example.todo.dto.TodoResponseDto;
import com.example.todo.entity.Status;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.mapper.TodoMapper;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Override
    public TodoResponseDto create(TodoRequestDto todoRequestDto, User user) {
        todoRequestDto.setUser(user);
        todoRequestDto.setStatus(Status.NOT_STARTED);
        Todo save = todoRepository.save(todoMapper.map(todoRequestDto));
        return todoMapper.map(save);
    }

    @Override
    public List<TodoResponseDto> getAll(int id) {
        List<Todo> allByUserId = todoRepository.getAllByUser_Id(id);
        if (allByUserId.size() == 0) {
            return null;
        }
        return todoMapper.mapList(allByUserId);
    }
}
