package com.example.todo.service.impl;

import com.example.todo.dto.TodoDto;
import com.example.todo.dto.TodoRequestDto;
import com.example.todo.dto.TodoResponseDto;
import com.example.todo.entity.Status;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.mapper.TodoMapper;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Todo> findAllByCategoryIdAndUserId(int categoryId, int id) {
        return todoRepository.findAllByCategoryIdAndUserId(categoryId, id);
    }

    @Override
    public TodoDto save(TodoDto todoDto, int id) {
        todoDto.setId(id);
        Todo save = todoRepository.save(todoMapper.map(todoDto));
        return todoMapper.mapToDto(save);
    }

    @Override
    public boolean delete(int id, int userId) {
        boolean delete = false;
        Optional<Todo> byId = todoRepository.findById(id);
        Todo todo = byId.get();
        if (todoRepository.existsById(id) && todo.getUser().getId() == userId) {
            todoRepository.deleteById(id);
            delete = true;
        }
        return delete;
    }
}
