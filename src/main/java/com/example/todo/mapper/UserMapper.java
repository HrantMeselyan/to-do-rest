package com.example.todo.mapper;

import com.example.todo.dto.CreateUserRequestDto;
import com.example.todo.dto.UserDto;
import com.example.todo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User map(CreateUserRequestDto dto);

    UserDto mapToDto(User entity);

}
