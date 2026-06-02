package com.example.todolists.domain.todo.entity;

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

    @Override
    public String toString() {
        return "ToDo [id=" + id + ", created_at=" + createdAt + ", title=" + title + ", description=" + description + ", status=" + status + ", due_time=" + dueTime + ", user=" + userId;
    }
}
