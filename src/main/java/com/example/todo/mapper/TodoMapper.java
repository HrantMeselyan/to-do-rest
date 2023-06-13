package com.example.todo.mapper;

import com.example.todo.dto.TodoDto;
import com.example.todo.dto.TodoRequestDto;
import com.example.todo.dto.TodoResponseDto;
import com.example.todo.entity.Todo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todo map(TodoRequestDto todoRequestDto);
    Todo map(TodoDto todoDto);
    TodoResponseDto map(Todo todo);
    TodoDto mapToDto(Todo todo);


    List<TodoResponseDto> mapList(List<Todo> allByUserId);
}
