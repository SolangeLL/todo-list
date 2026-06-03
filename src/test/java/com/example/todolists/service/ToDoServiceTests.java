package com.example.todolists.service;

import com.example.todolists.domain.common.ToDoStatus;
import com.example.todolists.domain.todo.dto.CreateToDoDto;
import com.example.todolists.domain.todo.dto.ToDoResponse;
import com.example.todolists.domain.todo.dto.UpdateToDoDto;
import com.example.todolists.domain.todo.entity.ToDo;
import com.example.todolists.domain.todo.repository.ToDoRepository;
import com.example.todolists.domain.todo.service.ToDoServiceImpl;
import com.example.todolists.security.UserDetails;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTests {

    @Mock
    private ToDoRepository toDoRepository;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private ToDoServiceImpl toDoService;

    @Test
    public void ToDoService_CreateToDo_ReturnToDoResponse() {
        UUID currentUserId = UUID.randomUUID();
        ToDo toDo = ToDo.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        CreateToDoDto createToDoDto = CreateToDoDto.builder()
                .title("Task")
                .description("This is a task")
                .dueTime(new Date())
                .status(ToDoStatus.TODO)
                .build();

        when(userDetails.getCurrentUserId()).thenReturn(currentUserId);
        when(toDoRepository.save(Mockito.any(ToDo.class))).thenReturn(toDo);
        ToDoResponse savedToDo = toDoService.createToDo(createToDoDto);

        Assertions.assertThat(savedToDo).isNotNull();
    }

    @Test
    public void ToDoService_FindAll_ReturnToDoResponses() {
        List<ToDo> toDos = new ArrayList<>();
        toDos.add(ToDo.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build());
        toDos.add(ToDo.builder()
                .title("Task2")
                .description("This is a task2")
                .status("IN_PROGRESS")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build());

        when(toDoRepository.findAll()).thenReturn(toDos);
        List<ToDoResponse> responses = toDoService.findAll();

        Assertions.assertThat(responses).isNotNull();
        Assertions.assertThat(responses.size()).isEqualTo(2);
    }

    @Test
    public void ToDoService_FindById_ReturnToDoResponse() {
        UUID expectedId = UUID.randomUUID();
        ToDo toDo = ToDo.builder()
                .id(expectedId)
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        when(toDoRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(toDo));
        ToDoResponse savedToDo = toDoService.findById(toDo.getId());

        Assertions.assertThat(savedToDo).isNotNull();
    }

    @Test
    public void ToDoService_FindByUserId_ReturnToDoResponse() {
        UUID expectedId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        List<ToDo> toDos = new ArrayList<>();
        toDos.add(ToDo.builder()
                .id(expectedId)
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(userId)
                .build());
        toDos.add(ToDo.builder()
                .id(expectedId)
                .title("Task2")
                .description("This is a task2")
                .status("TODO")
                .dueTime(new Date())
                .userId(userId)
                .build());

        when(toDoRepository.findByUserId(Mockito.any(UUID.class))).thenReturn(Optional.of(toDos));
        List<ToDoResponse> foundToDos = toDoService.findByUserId(userId);

        Assertions.assertThat(foundToDos).isNotNull();
        Assertions.assertThat(foundToDos.size()).isEqualTo(2);
    }

    @Test
    public void ToDoService_UpdateById_ReturnToDoResponse() {
        UUID expectedId = UUID.randomUUID();
        ToDo toDo = ToDo.builder()
                .id(expectedId)
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        UpdateToDoDto updateToDoDto = UpdateToDoDto.builder()
                .title(Optional.of("New task"))
                .description(Optional.of("update"))
                .build();

        when(toDoRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(toDo));
        when(toDoRepository.save(Mockito.any(ToDo.class))).thenAnswer(returnsFirstArg());
        ToDoResponse updatedToDo = toDoService.updateToDo(expectedId, updateToDoDto);

        Assertions.assertThat(updatedToDo).isNotNull();
        Assertions.assertThat(updatedToDo.getTitle()).isEqualTo("New task");
        Assertions.assertThat(updatedToDo.getDescription()).isEqualTo("update");
    }

    @Test
    public void ToDoService_DeleteById_ReturnToDoResponse() {
        UUID expectedId = UUID.randomUUID();
        UUID currentUserId = UUID.randomUUID();
        ToDo toDo = ToDo.builder()
                .id(expectedId)
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(currentUserId)
                .build();

        when(userDetails.getCurrentUserId()).thenReturn(currentUserId);
        when(toDoRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(toDo));

        assertAll(() -> toDoService.deleteToDoById(expectedId));
    }

}
