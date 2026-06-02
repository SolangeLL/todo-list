package com.example.todolists.domain.todo.controller;

import com.example.todolists.domain.todo.dto.CreateToDoDto;
import com.example.todolists.domain.todo.dto.ToDoResponse;
import com.example.todolists.domain.todo.dto.UpdateToDoDto;
import com.example.todolists.domain.todo.service.ToDoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public List<ToDoResponse> findAllTodos() {
        return toDoService.findAll();
    }

    @GetMapping("/{id}")
    public ToDoResponse findToDoById(@PathVariable UUID id) {
        return toDoService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public List<ToDoResponse> findTodoByUserId(@PathVariable UUID userId) {
        return toDoService.findByUserId(userId);
    }

    @GetMapping("/user")
    public List<ToDoResponse> findToDoByCurrentUserId() {
        return toDoService.findByCurrentUserId();
    }


    @PostMapping
    public ResponseEntity<ToDoResponse> createToDo(@RequestBody @Valid CreateToDoDto dto) {
        ToDoResponse toDo = toDoService.createToDo(dto);
        URI location = URI.create("/todos/" + toDo.getId());
        return ResponseEntity.created(location).body(toDo);
    }

    @PatchMapping("/{id}")
    public ToDoResponse updateToDo(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateToDoDto dto
    ) {
        return toDoService.updateToDo(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToDo(@PathVariable UUID id) {
        toDoService.deleteToDo(id);
        return ResponseEntity.noContent().build();
    }
}
