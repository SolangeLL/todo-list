package com.example.todolists.domain.todo.entity;

import com.example.todolists.domain.todo.dto.ToDoResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "todo")
public class ToDo {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Builder.Default
    @Column(name="created_at")
    private Date createdAt = new Date();

    @Setter
    @Column(name="title")
    private String title;

    @Setter
    @Column(name="description")
    private String description;

    @Setter
    @Column(name="status")
    private String status;

    @Setter
    @Column(name="due_time")
    private Date dueTime;

    @Column(name="user_id")
    private UUID userId;

    public ToDo(UUID userId) {
        this.userId = userId;
    }

    public ToDo() {

    }

    public static ToDo fromToDoResponse(ToDoResponse toDoResponse) {
        return ToDo.builder()
                .id(toDoResponse.getId())
                .userId(toDoResponse.getUserId())
                .title(toDoResponse.getTitle())
                .description(toDoResponse.getDescription())
                .dueTime(toDoResponse.getDueTime())
                .status(toDoResponse.getStatus())
                .createdAt(toDoResponse.getCreatedAt())
                .build();
    }

    @Override
    public String toString() {
        return "ToDo [id=" + id + ", created_at=" + createdAt + ", title=" + title + ", description=" + description + ", status=" + status + ", due_time=" + dueTime + ", user=" + userId;
    }
}
