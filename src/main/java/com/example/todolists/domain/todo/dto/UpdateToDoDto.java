package com.example.todolists.domain.todo.dto;

import com.example.todolists.domain.common.ToDoStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateToDoDto {
    private Optional<String> title = Optional.empty();

    private Optional<String> description = Optional.empty();

    @DateTimeFormat
    private Optional<Date> dueTime = Optional.empty();

    private Optional<ToDoStatus> status = Optional.empty();
}
