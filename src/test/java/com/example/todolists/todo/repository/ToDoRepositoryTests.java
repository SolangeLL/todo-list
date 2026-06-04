package com.example.todolists.todo.repository;

import com.example.todolists.domain.todo.entity.ToDo;
import com.example.todolists.domain.todo.repository.ToDoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ToDoRepositoryTests {

    @Autowired
    private ToDoRepository toDoRepository;

    @Test
    public void ToDoRepository_Save_ReturnSavedToDo() {
        // Arrange
        ToDo toDo = ToDo.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        // Act
        ToDo savedTodo = toDoRepository.save(toDo);

        // Assert
        Assertions.assertThat(savedTodo).isNotNull();
        Assertions.assertThat(savedTodo.getId()).isNotNull();
    }

    @Test
    public void ToDoRepository_FindAll_ReturnMoreThanOneToDo() {
        ToDo toDo = ToDo.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();
        ToDo toDo2 = ToDo.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        toDoRepository.save(toDo);
        toDoRepository.save(toDo2);
        List<ToDo> toDos = toDoRepository.findAll();

        Assertions.assertThat(toDos).isNotNull();
        Assertions.assertThat(toDos.size()).isEqualTo(2);
    }

    @Test
    public void ToDoRepository_FindById_ReturnToDoNotNull() {
        ToDo toDo = ToDo.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        toDoRepository.save(toDo);
        ToDo foundToDo = toDoRepository.findById(toDo.getId()).get();

        Assertions.assertThat(foundToDo).isNotNull();
    }

    @Test
    public void ToDoRepository_FindByUserId_ReturnToDoNotNull() {
        ToDo toDo = ToDo.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        toDoRepository.save(toDo);
        List<ToDo> toDos = toDoRepository.findByUserId(toDo.getUserId()).get();

        Assertions.assertThat(toDos).isNotNull();
    }

    @Test
    public void ToDoRepository_Update_ReturnToDoNotNull() {
        ToDo toDo = ToDo.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        toDoRepository.save(toDo);
        ToDo toDoSaved = toDoRepository.findById(toDo.getId()).get();
        toDoSaved.setTitle("Updated Task");
        toDoSaved.setDescription("I've been updated");
        ToDo updatedToDo = toDoRepository.save(toDoSaved);

        Assertions.assertThat(updatedToDo).isNotNull();
        Assertions.assertThat(updatedToDo.getTitle()).isEqualTo("Updated Task");
        Assertions.assertThat(updatedToDo.getDescription()).isEqualTo("I've been updated");
    }

    @Test
    public void ToDoRepository_Delete_ReturnEmptyToDo() {
        ToDo toDo = ToDo.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        toDoRepository.save(toDo);
        toDoRepository.deleteById(toDo.getId());
        Optional<ToDo> toDoReturn = toDoRepository.findById(toDo.getId());

        Assertions.assertThat(toDoReturn).isEmpty();
    }
}
