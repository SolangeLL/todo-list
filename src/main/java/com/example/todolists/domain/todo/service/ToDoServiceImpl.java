package com.example.todolists.domain.todo.service;

import com.example.todolists.domain.todo.dto.ToDoResponse;
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
public class ToDoServiceImpl implements ToDoService {
    private final ToDoRepository toDoRepository;

    public ToDoServiceImpl(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public List<ToDoResponse> findAll() {
        List<ToDo> toDos = toDoRepository.findAll();
        return toDos.stream().map(ToDoResponse::fromToDo).toList();
    }

    public ToDoResponse findById(UUID id) {
        ToDo toDo = toDoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ToDo (id: " + id + ") not found"));
        return ToDoResponse.fromToDo(toDo);
    }

    public List<ToDoResponse> findByUserId(UUID userId) {
        List<ToDo> toDos = toDoRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("ToDo (userId: " + userId + ") not found")
        );
        return toDos.stream().map(ToDoResponse::fromToDo).toList();
    }

    public ToDoResponse createToDo(CreateToDoDto dto) {
        UserDetails userDetails = new UserDetails();
        ToDo toDo = new ToDo(userDetails.getCurrentUserId());
        toDo.setTitle(dto.getTitle());
        toDo.setDescription(dto.getDescription());
        toDo.setStatus(dto.getStatus().toString());
        toDo.setDueTime(dto.getDueTime());

        ToDo savedToDo = toDoRepository.save(toDo);
        return ToDoResponse.fromToDo(savedToDo);
    }

    public ToDoResponse updateToDo(UUID id, UpdateToDoDto dto) {
        ToDoResponse updatedToDoResponse = findById(id);
        ToDo updatedToDo = ToDo.fromToDoResponse(updatedToDoResponse);
        dto.getTitle().ifPresent(updatedToDo::setTitle);
        dto.getDescription().ifPresent(updatedToDo::setDescription);
        dto.getDueTime().ifPresent(updatedToDo::setDueTime);
        dto.getStatus().ifPresent(status -> updatedToDo.setStatus(status.toString()));
        ToDo saved = toDoRepository.save(updatedToDo);
        return ToDoResponse.fromToDo(saved);
    }

    public List<ToDoResponse> findByCurrentUserId() {
        UserDetails userDetails = new UserDetails();
        UUID currentUserId = userDetails.getCurrentUserId();
        return findByUserId(currentUserId);
    }

    public void deleteToDoById(UUID id) {
        UserDetails userDetails = new UserDetails();
        ToDoResponse toDoResponse = findById(id);
        boolean isSameUserId = userDetails.getCurrentUserId().equals(toDoResponse.getUserId());
        if (!isSameUserId)
            throw new AuthorizationDeniedException("You don't have the rights to delete this Todo (id: " + id + ")");
        toDoRepository.deleteById(id);
    }
}
