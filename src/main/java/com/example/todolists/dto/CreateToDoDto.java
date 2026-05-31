package com.example.todolists.dto;

import com.example.todolists.ToDoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CreateToDoDto {
    @NotBlank(message = "Le titre est requis")
    private String title;

    @NotBlank(message = "Le description est requis")
    private String description;

    @NotNull(message = "La date butoire est requise")
    @DateTimeFormat
    private Date dueTime;

    private ToDoStatus status = ToDoStatus.TODO;

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

    public ToDoStatus getStatus() {
        return status;
    }

    public void setStatus(ToDoStatus status) {
        this.status = status;
    }
}
