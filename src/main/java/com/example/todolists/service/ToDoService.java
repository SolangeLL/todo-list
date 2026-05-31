package com.example.todolists.service;

import com.example.todolists.dto.CreateToDoDto;
import com.example.todolists.dto.UpdateToDoDto;
import com.example.todolists.exception.ResourceNotFoundException;
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

    public ToDoModel findToDoById(UUID id) {
        return toDoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ToDo (id: " + id + ") not found"));
    }

    public ToDoModel createToDo(CreateToDoDto dto) {
        ToDoModel model = new ToDoModel();
        model.setTitle(dto.getTitle());
        model.setDescription(dto.getDescription());
        model.setStatus(dto.getStatus().toString());
        model.setDueTime(dto.getDueTime());
        return toDoRepository.save(model);
    }

    public ToDoModel updateToDo(UpdateToDoDto dto) {
        ToDoModel updatedToDo = findToDoById(dto.getId());
        dto.getTitle().ifPresent(updatedToDo::setTitle);
        dto.getDescription().ifPresent(updatedToDo::setDescription);
        dto.getDueTime().ifPresent(updatedToDo::setDueTime);
        dto.getStatus().ifPresent(status -> updatedToDo.setStatus(status.toString()));
        return toDoRepository.save(updatedToDo);
    }

    public List<ToDoModel> findByUserId(UUID userId) {
        return toDoRepository.findByUserId(userId);
    }
}
