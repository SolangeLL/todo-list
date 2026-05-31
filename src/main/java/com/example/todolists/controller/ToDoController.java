package com.example.todolists.controller;

import com.example.todolists.dto.CreateToDoDto;
import com.example.todolists.dto.UpdateToDoDto;
import com.example.todolists.model.ToDoModel;
import com.example.todolists.service.ToDoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ToDoModel>> findAllTodos() {
        return ResponseEntity.ok(toDoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoModel> findToDoById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(toDoService.findToDoById(id));
    }

    @PostMapping
    public ResponseEntity<ToDoModel> createToDo(@RequestBody @Valid CreateToDoDto dto) {
        return ResponseEntity.ok(toDoService.createToDo(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ToDoModel> updateToDo(@RequestBody @Valid UpdateToDoDto dto) {
        return ResponseEntity.ok(toDoService.updateToDo(dto));
    }

    @DeleteMapping("/{id}")
    public void deleteToDo() {

    }

    @GetMapping("user/{userid}")
    public List<ToDoModel> findTodoByUserId(@PathVariable("userid") UUID userId) {
        return toDoService.findByUserId(userId);
    }
}
