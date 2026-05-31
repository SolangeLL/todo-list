package com.example.todolists.dto;

import com.example.todolists.ToDoStatus;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Optional;

public class UpdateToDoDto {
    private Optional<String> title = Optional.empty();

    private Optional<String> description = Optional.empty();

    @DateTimeFormat
    private Optional<Date> dueTime = Optional.empty();

    private Optional<ToDoStatus> status = Optional.empty();

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Optional<Date> getDueTime() {
        return dueTime;
    }

    public Optional<ToDoStatus> getStatus() {
        return status;
    }
}
