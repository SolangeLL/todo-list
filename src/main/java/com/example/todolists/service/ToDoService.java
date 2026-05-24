package com.example.todolists.service;

import com.example.todolists.model.ToDoModel;
import com.example.todolists.repository.ToDoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public List<ToDoModel> findAll() {
        return toDoRepository.findAll();
    }

    public List<ToDoModel> findByUserId(UUID userId) {
        return toDoRepository.findByUserId(userId);
    }
}
