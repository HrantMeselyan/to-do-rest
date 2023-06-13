package com.example.todo.repository;

import com.example.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> getAllByUser_Id(int id);
    List<Todo> findAllByUserIdAndStatus(int userId, String status);
    List<Todo> findAllByCategoryIdAndUserId(int categoryId, int userId);
}
