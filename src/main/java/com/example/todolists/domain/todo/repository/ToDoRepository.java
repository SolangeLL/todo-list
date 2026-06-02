package com.example.todolists.domain.todo.repository;

import com.example.todolists.domain.todo.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToDoRepository extends JpaRepository<ToDo, UUID> {
    Optional<List<ToDo>> findByUserId(UUID userid);
}
