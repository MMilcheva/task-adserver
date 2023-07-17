package com.example.taskadserver.models;

import com.example.taskadserver.models.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", insertable = false)
    private Long taskId;

    @NotNull(message = "Title cannot be empty")
    @Column(name = "title")
    @Size(min = 5, max = 50, message = "Title has to be between 5 and 50 symbols!")
    private String title;
    @NotNull(message = "Task description cannot be empty")
    @Column(name = "description")
    @Size(min = 5, max = 1000, message = "Task description has to be between 5 and 1000 symbols!")
    private String taskDescription;

    @NotNull(message = "Due date cannot be empty")
    @FutureOrPresent(message = "Due date cannot be earlier than today")
    @Column(name = "due_date")
    private LocalDate dueDate;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public Task() {
    }

    public Task(Long taskId, String title, String taskDescription, LocalDate dueDate, User user) {
        this.taskId = taskId;
        this.title = title;
        this.taskDescription = taskDescription;
        this.dueDate = dueDate;
        this.user = user;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskId, task.taskId);
    }

    public Long getTaskId() {
        return taskId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }
}
