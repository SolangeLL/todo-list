package com.example.todolists.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "todo")
public class ToDoModel {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="created_at")
    private Date createdAt = new Date();

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="status")
    private String status;

    @Column(name="due_time")
    private Date dueTime;

    @Column(name="user_id")
    private UUID userId;

    public ToDoModel(UUID userId) {
        this.userId = userId;
    }

    public ToDoModel() {

    }

    public UUID getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setDueTime(Date newDueTime) {
        this.dueTime = newDueTime;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public UUID getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "ToDo [id=" + id + ", created_at=" + createdAt + ", title=" + title + ", description=" + description + ", status=" + status + ", due_time=" + dueTime + ", user=" + userId;
    }
}
