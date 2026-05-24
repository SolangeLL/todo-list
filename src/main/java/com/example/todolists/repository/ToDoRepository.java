package com.example.todolists.repository;

import com.example.todolists.model.ToDoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ToDoRepository extends JpaRepository<ToDoModel, UUID> {
    List<ToDoModel> findByUserId(UUID userid);
}
