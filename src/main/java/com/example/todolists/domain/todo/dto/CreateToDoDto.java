package com.example.todolists.domain.todo.dto;

import com.example.todolists.domain.common.ToDoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateToDoDto {
    @NotBlank(message = "The title is required")
    private String title;

    @NotBlank(message = "The description is required")
    private String description;

    @NotNull(message = "The due time is required")
    @DateTimeFormat
    private Date dueTime;

    private ToDoStatus status = ToDoStatus.TODO;
}
