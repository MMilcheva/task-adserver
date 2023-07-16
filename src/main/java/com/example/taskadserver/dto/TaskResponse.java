package com.example.taskadserver.dto;

import com.example.taskadserver.models.enums.TaskStatus;

import java.time.LocalDate;

public class TaskResponse {
    private String title;
    private String description;
    private String username;
    private LocalDate dueDate;
    private TaskStatus status;

    public TaskResponse() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getTaskStatus() {
        return status;
    }

    public void setTaskStatus(TaskStatus status) {
        this.status = status;
    }
}
