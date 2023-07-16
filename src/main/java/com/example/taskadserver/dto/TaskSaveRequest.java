package com.example.taskadserver.dto;


import com.example.taskadserver.models.User;
import com.example.taskadserver.models.enums.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public class TaskSaveRequest {
    @NotNull(message = "Title cannot be empty")
    @Column(name = "title")
    @Size(min = 5, max = 50, message = "Task description has to be between 5 and 50 symbols!")
    private String title;
    @NotNull(message = "Task description cannot be empty")
    @Column(name = "description")
    @Size(min = 5, max = 1000, message = "Task description has to be between 5 and 1000 symbols!")
    private String taskDescription;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @NotNull(message = "Due date cannot be empty")
    @FutureOrPresent(message = "Due date cannot be earlier than today")
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "user_id")
    private User user;

    public TaskSaveRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public TaskStatus getTaskStatus() {
        return status;
    }

    public void setTaskStatus(TaskStatus status) {
        this.status = status;
    }


    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            this.dueDate = LocalDate.now();
        } else {
            this.dueDate = dueDate;
        }
    }
}
