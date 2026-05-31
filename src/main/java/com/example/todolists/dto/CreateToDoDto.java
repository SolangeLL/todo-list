package com.example.todolists.dto;

import com.example.todolists.ToDoStatus;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

public class CreateToDoDto {
    @NotBlank(message = "Le titre est requis")
    private String title;

    @NotBlank(message = "Le description est requis")
    private String description;

    @NotBlank(message = "La date butoire est requise")
    @DateTimeFormat
    private Date dueTime;

    @NotBlank(message = "L'user_id est requis")
    private UUID userId;

    @NotBlank(message = "Le status est requis")
    private ToDoStatus status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public ToDoStatus getStatus() {
        return status;
    }

    public void setStatus(ToDoStatus status) {
        this.status = status;
    }
}
