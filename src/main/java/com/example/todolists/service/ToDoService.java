package com.example.todolists.service;

import com.example.todolists.component.AuthUtils;
import com.example.todolists.dto.CreateToDoDto;
import com.example.todolists.dto.UpdateToDoDto;
import com.example.todolists.exception.ResourceNotFoundException;
import com.example.todolists.model.ToDoModel;
import com.example.todolists.repository.ToDoRepository;
import org.springframework.security.authorization.AuthorizationDeniedException;
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

    public ToDoModel findById(UUID id) {
        return toDoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ToDo (id: " + id + ") not found"));
    }

    public ToDoModel createToDo(CreateToDoDto dto) {
        AuthUtils auth = new AuthUtils();
        ToDoModel model = new ToDoModel(auth.getCurrentUserId());
        model.setTitle(dto.getTitle());
        model.setDescription(dto.getDescription());
        model.setStatus(dto.getStatus().toString());
        model.setDueTime(dto.getDueTime());
        return toDoRepository.save(model);
    }

    public ToDoModel updateToDo(UUID id, UpdateToDoDto dto) {
        ToDoModel updatedToDo = findById(id);
        dto.getTitle().ifPresent(updatedToDo::setTitle);
        dto.getDescription().ifPresent(updatedToDo::setDescription);
        dto.getDueTime().ifPresent(updatedToDo::setDueTime);
        dto.getStatus().ifPresent(status -> updatedToDo.setStatus(status.toString()));
        return toDoRepository.save(updatedToDo);
    }

    public List<ToDoModel> findByUserId(UUID userId) {
        return toDoRepository.findByUserId(userId);
    }

    public List<ToDoModel> findByCurrentUserId() {
        AuthUtils auth = new AuthUtils();
        return toDoRepository.findByUserId(auth.getCurrentUserId());
    }

    public void deleteToDo(UUID id) {
        AuthUtils auth = new AuthUtils();
        ToDoModel toDo = findById(id);
        boolean isSameUserId = auth.getCurrentUserId().equals(toDo.getUserId());
        if (!isSameUserId)
            throw new AuthorizationDeniedException("You don't have the rights to delete this Todo (id: " + id + ")");
        toDoRepository.deleteById(id);
    }
}
