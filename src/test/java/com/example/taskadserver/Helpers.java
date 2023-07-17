package com.example.taskadserver;

import com.example.taskadserver.models.Task;
import com.example.taskadserver.models.TaskFilterOptions;
import com.example.taskadserver.models.User;
import com.example.taskadserver.models.enums.TaskStatus;

import java.time.LocalDate;

public class Helpers {

    public static Task createMockTask() {
        Task task = new Task();
        task.setTaskId(1L);
        task.setUser(createMockUser());
        task.setTitle("Test-task-title");
        task.setTaskDescription("Test-task-description");
        task.setTaskStatus(TaskStatus.PENDING);
        task.setDueDate(LocalDate.of(2023, 07, 17));
        return task;
    }
    public static Task createMockTask2() {
        Task task = new Task();
        task.setTaskId(1L);
        task.setUser(createMockUser());
        task.setTitle("Test-task-title2");
        task.setTaskDescription("Test-task-description2");
        task.setTaskStatus(TaskStatus.COMPLETED);
        task.setDueDate(LocalDate.of(2023, 07, 17));
        return task;
    }
    public static Task createMockTask3() {
        Task task = new Task();
        task.setTaskId(1L);
        task.setUser(createMockUser());
        task.setTitle("Test-task-title2");
        task.setTaskDescription("Test-task-description2");
        task.setTaskStatus(TaskStatus.DISCARDED);
        task.setDueDate(LocalDate.of(2023, 07, 17));
        return task;
    }

    public static User createMockUser() {
        var mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setFirstName("MockFirstName");
        mockUser.setLastName("MockLastName");
        mockUser.setUsername("MockUsername");
        mockUser.setPassword("MockPassword");
        return mockUser;
    }

    public static TaskFilterOptions createMockTaskFilterOptions() {
        return new TaskFilterOptions(
                1L,
                "Test-task-title",
                "Test-task-description",
                TaskStatus.PENDING,
                LocalDate.parse("2023-03-09"),
                "dueDate",
                "asc");
    }
}
