package com.example.todolists.domain.todo.dto;

import com.example.todolists.domain.common.ToDoStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Optional;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateToDoDto {
    @Builder.Default
    private Optional<String> title = Optional.empty();

    @Builder.Default
    private Optional<String> description = Optional.empty();

    @DateTimeFormat
    @Builder.Default
    private Optional<Date> dueTime = Optional.empty();

    @Builder.Default
    private Optional<ToDoStatus> status = Optional.empty();
}
