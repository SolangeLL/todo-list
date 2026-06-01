package com.example.todolists.repository;

import com.example.todolists.model.ToDoModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Date;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ToDoRepositoryTests {

    @Autowired
    private ToDoRepository toDoRepository;

    @Test
    public void ToDoRepository_SaveAll_ReturnSavedToDo() {

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
}
