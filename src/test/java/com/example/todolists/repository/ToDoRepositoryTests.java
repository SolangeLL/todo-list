package com.example.todolists.repository;

import com.example.todolists.model.ToDoModel;
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
        ToDoModel toDo = ToDoModel.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        // Act
        ToDoModel savedTodo = toDoRepository.save(toDo);

        // Assert
        Assertions.assertThat(savedTodo).isNotNull();
        Assertions.assertThat(savedTodo.getId()).isNotNull();
    }

    @Test
    public void ToDoRepository_FindAll_ReturnMoreThanOneToDo() {
        ToDoModel toDo = ToDoModel.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();
        ToDoModel toDo2 = ToDoModel.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        toDoRepository.save(toDo);
        toDoRepository.save(toDo2);
        List<ToDoModel> toDos = toDoRepository.findAll();

        Assertions.assertThat(toDos).isNotNull();
        Assertions.assertThat(toDos.size()).isEqualTo(2);
    }

    @Test
    public void ToDoRepository_FindById_ReturnToDoNotNull() {
        ToDoModel toDo = ToDoModel.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        toDoRepository.save(toDo);
        ToDoModel foundToDo = toDoRepository.findById(toDo.getId()).get();

        Assertions.assertThat(foundToDo).isNotNull();
    }

    @Test
    public void ToDoRepository_FindByUserId_ReturnToDoNotNull() {
        ToDoModel toDo = ToDoModel.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        toDoRepository.save(toDo);
        List<ToDoModel> toDos = toDoRepository.findByUserId(toDo.getUserId()).get();

        Assertions.assertThat(toDos).isNotNull();
    }

    @Test
    public void ToDoRepository_Update_ReturnToDoNotNull() {
        ToDoModel toDo = ToDoModel.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        toDoRepository.save(toDo);
        ToDoModel toDoSaved = toDoRepository.findById(toDo.getId()).get();
        toDoSaved.setTitle("Updated Task");
        toDoSaved.setDescription("I've been updated");
        ToDoModel updatedToDo = toDoRepository.save(toDoSaved);

        Assertions.assertThat(updatedToDo).isNotNull();
        Assertions.assertThat(updatedToDo.getTitle()).isEqualTo("Updated Task");
        Assertions.assertThat(updatedToDo.getDescription()).isEqualTo("I've been updated");
    }

    @Test
    public void ToDoRepository_Delete_ReturnEmptyToDo() {
        ToDoModel toDo = ToDoModel.builder()
                .title("Task")
                .description("This is a task")
                .status("TODO")
                .dueTime(new Date())
                .userId(UUID.randomUUID())
                .build();

        toDoRepository.save(toDo);
        toDoRepository.deleteById(toDo.getId());
        Optional<ToDoModel> toDoReturn = toDoRepository.findById(toDo.getId());

        Assertions.assertThat(toDoReturn).isEmpty();
    }
}
