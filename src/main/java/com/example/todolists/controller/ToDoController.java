package com.example.todolists.controller;

import com.example.todolists.model.ToDoModel;
import com.example.todolists.service.ToDoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todos")
class ToDoController {
    private final ToDoService toDoService;

    ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public List<ToDoModel> findAllTodos() {
        return toDoService.findAll();
    }

    @GetMapping("user/{userid}")
    public List<ToDoModel> findTodoByUserId(@PathVariable("userid") UUID userId) {
        return toDoService.findByUserId(userId);
    }
}
