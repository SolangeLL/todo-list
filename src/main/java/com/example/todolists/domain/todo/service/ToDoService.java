package com.example.todolists.domain.todo.service;

import com.example.todolists.security.UserDetails;
import com.example.todolists.domain.todo.dto.CreateToDoDto;
import com.example.todolists.domain.todo.dto.UpdateToDoDto;
import com.example.todolists.exception.ResourceNotFoundException;
import com.example.todolists.domain.todo.entity.ToDo;
import com.example.todolists.domain.todo.repository.ToDoRepository;
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

    public List<ToDo> findAll() {
        return toDoRepository.findAll();
    }

    public ToDo findById(UUID id) {
        return toDoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ToDo (id: " + id + ") not found"));
    }

    public ToDo createToDo(CreateToDoDto dto) {
        UserDetails userDetails = new UserDetails();
        ToDo toDo = new ToDo(userDetails.getCurrentUserId());
        toDo.setTitle(dto.getTitle());
        toDo.setDescription(dto.getDescription());
        toDo.setStatus(dto.getStatus().toString());
        toDo.setDueTime(dto.getDueTime());
        return toDoRepository.save(toDo);
    }

    public ToDo updateToDo(UUID id, UpdateToDoDto dto) {
        ToDo updatedToDo = findById(id);
        dto.getTitle().ifPresent(updatedToDo::setTitle);
        dto.getDescription().ifPresent(updatedToDo::setDescription);
        dto.getDueTime().ifPresent(updatedToDo::setDueTime);
        dto.getStatus().ifPresent(status -> updatedToDo.setStatus(status.toString()));
        return toDoRepository.save(updatedToDo);
    }

    public List<ToDo> findByUserId(UUID userId) {
        return toDoRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("ToDo (userId: " + userId + ") not found")
        );
    }

    public List<ToDo> findByCurrentUserId() {
        UserDetails userDetails = new UserDetails();
        UUID currentUserId = userDetails.getCurrentUserId();
        return toDoRepository.findByUserId(currentUserId).orElseThrow(
                () -> new ResourceNotFoundException("ToDo (userId: " + currentUserId + ") not found")
        );
    }

    public void deleteToDo(UUID id) {
        UserDetails userDetails = new UserDetails();
        ToDo toDo = findById(id);
        boolean isSameUserId = userDetails.getCurrentUserId().equals(toDo.getUserId());
        if (!isSameUserId)
            throw new AuthorizationDeniedException("You don't have the rights to delete this Todo (id: " + id + ")");
        toDoRepository.deleteById(id);
    }
}
