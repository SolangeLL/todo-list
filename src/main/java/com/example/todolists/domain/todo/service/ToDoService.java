package com.example.todolists.domain.todo.service;

import com.example.todolists.domain.todo.dto.ToDoResponse;
import com.example.todolists.domain.todo.dto.CreateToDoDto;
import com.example.todolists.domain.todo.dto.UpdateToDoDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ToDoService {
    List<ToDoResponse> findAll();
    ToDoResponse findById(UUID id);
    List<ToDoResponse> findByUserId(UUID id);
    List<ToDoResponse> findByCurrentUserId();
    ToDoResponse createToDo(CreateToDoDto dto);
    ToDoResponse updateToDo(UUID id, UpdateToDoDto dto);
    void deleteToDoById(UUID id);
}
