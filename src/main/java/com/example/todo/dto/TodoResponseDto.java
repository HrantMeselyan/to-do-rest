package com.example.todo.dto;

import com.example.todo.entity.Category;
import com.example.todo.entity.Status;
import com.example.todo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponseDto {
    private int id;
    private String title;
    private Status status;
    Category category;
    User user;
}
