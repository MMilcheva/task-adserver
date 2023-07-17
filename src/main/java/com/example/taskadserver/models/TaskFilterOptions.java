package com.example.taskadserver.models;

import com.example.taskadserver.models.enums.TaskStatus;

import java.time.LocalDate;
import java.util.Optional;

public class TaskFilterOptions {
    private Optional<Long> userId;
    private Optional<String> title;
    private Optional<String> taskDescription;
    private Optional<TaskStatus> status;
    private Optional<LocalDate> dueDate;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public TaskFilterOptions(Long userId,
                             String title,
                             String taskDescription,
                             TaskStatus status,
                             LocalDate dueDate,
                             String sortBy,
                             String sortOrder) {
        this.userId = Optional.ofNullable(userId);
        this.title = Optional.ofNullable(title);
        this.taskDescription = Optional.ofNullable(taskDescription);
        this.dueDate = Optional.ofNullable(dueDate);
        this.status = Optional.ofNullable(status);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }

    public TaskFilterOptions() {
    }

    public Optional<Long> getUserId() {
        return userId;
    }

    public void setUserId(Optional<Long> userId) {
        this.userId = userId;
    }

    public Optional<String> getTitle() {
        return title;
    }

    public void setTitle(Optional<String> title) {
        this.title = title;
    }

    public Optional<String> getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(Optional<String> taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Optional<TaskStatus> getTaskStatus() {
        return status;
    }

    public void setStatus(Optional<TaskStatus> status) {
        this.status = status;
    }

    public Optional<LocalDate> getDueDate() {
        return dueDate;
    }

    public void setDueDate(Optional<LocalDate> dueDate) {
        this.dueDate = dueDate;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public void setSortBy(Optional<String> sortBy) {
        this.sortBy = sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Optional<String> sortOrder) {
        this.sortOrder = sortOrder;
    }
}
