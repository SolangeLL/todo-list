package com.example.todolists.controller;

import com.example.todolists.dto.CreateToDoDto;
import com.example.todolists.dto.UpdateToDoDto;
import com.example.todolists.model.ToDoModel;
import com.example.todolists.service.ToDoService;
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
    public List<ToDoModel> findAllTodos() {
        return toDoService.findAll();
    }

    @GetMapping("/{id}")
    public ToDoModel findToDoById(@PathVariable UUID id) {
        return toDoService.findById(id);
    }

    @GetMapping("user/{userId}")
    public List<ToDoModel> findTodoByUserId(@PathVariable UUID userId) {
        return toDoService.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<ToDoModel> createToDo(@RequestBody @Valid CreateToDoDto dto) {
        ToDoModel toDo = toDoService.createToDo(dto);
        URI location = URI.create("/todos/" + toDo.getId());
        return ResponseEntity.created(location).body(toDo);
    }

    @PatchMapping("/{id}")
    public ToDoModel updateToDo(
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
