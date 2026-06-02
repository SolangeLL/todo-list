package com.example.todolists.domain.common;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ToDoStatus {
    TODO,
    IN_PROGRESS,
    DONE;

    @JsonCreator
    public static ToDoStatus fromValue(String value) {
        for (ToDoStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status : '" + value + "'. Accepted values : TODO, IN_PROGRESS, DONE");
    }
}
