package com.example.todolists.domain.todo.dto;

import com.example.todolists.domain.todo.entity.ToDo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToDoResponse {
    private UUID id;
    private UUID userId;
    private String title;
    private String description;
    private Date dueTime;
    private String status;
    private Date createdAt;

    public static ToDoResponse fromToDo(ToDo toDo) {
        return ToDoResponse.builder()
                .id(toDo.getId())
                .userId(toDo.getUserId())
                .title(toDo.getTitle())
                .description(toDo.getDescription())
                .dueTime(toDo.getDueTime())
                .status(toDo.getStatus())
                .createdAt(toDo.getCreatedAt())
                .build();
    }
}