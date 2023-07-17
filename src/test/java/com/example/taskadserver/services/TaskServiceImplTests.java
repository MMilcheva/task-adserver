package com.example.taskadserver.services;

import com.example.taskadserver.models.Task;
import com.example.taskadserver.models.TaskFilterOptions;
import com.example.taskadserver.models.enums.TaskStatus;
import com.example.taskadserver.repositories.contracts.TaskRepository;
import com.example.taskadserver.services.contracts.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;

import static com.example.taskadserver.Helpers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTests {
    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskServiceImpl(taskRepository);
    }

    @Test
    void getTaskById_ShouldReturnTaskWithMatchingId() {
        // Arrange
        Long taskId = 1L;
        Task expectedTask = createMockTask();
        when(taskRepository.getById(taskId)).thenReturn(expectedTask);

        // Act
        Task actualTask = taskService.getTaskById(taskId);

        // Assert
        assertEquals(expectedTask, actualTask);
        verify(taskRepository, times(1)).getById(taskId);
    }

    @Test
    void getAllTasksFilter_ShouldReturnFilteredTasks() {
        // Arrange
        TaskFilterOptions filterOptions = new TaskFilterOptions();
        List<Task> expectedTasks = Arrays.asList(
                createMockTask(),
                createMockTask2());
        when(taskRepository.getAllTasksFilter(filterOptions)).thenReturn(expectedTasks);

        // Act
        List<Task> actualTasks = taskService.getAllTasksFilter(filterOptions);

        // Assert
        assertEquals(expectedTasks, actualTasks);
        verify(taskRepository, times(1)).getAllTasksFilter(filterOptions);
    }

    @Test
    void createTask_ShouldSetPendingStatusAndCreateTask() {
        // Arrange
        Task task = createMockTask();

        // Act
        Task createdTask = taskService.createTask(task);

        // Assert
        assertEquals(TaskStatus.PENDING, createdTask.getTaskStatus());
        verify(taskRepository, times(1)).create(task);
    }

    @Test
    void updateTask_ShouldUpdateTaskAndSetDueDateForCompletedOrDiscardedTasks() {
        // Arrange
        Task task = createMockTask();

        // Act
        Task updatedTask = taskService.updateTask(task);

        // Assert
        assertEquals(LocalDate.now(), updatedTask.getDueDate());
        verify(taskRepository, times(1)).update(task);
    }

    @Test
    void completeTask_ShouldSetTaskStatusToCompletedAndUpdateTask() {
        // Arrange
        Task task = createMockTask2();

        // Act
        Task completedTask = taskService.completeTask(task);

        // Assert
        assertEquals(TaskStatus.COMPLETED, completedTask.getTaskStatus());
        verify(taskRepository, times(1)).update(task);
    }

    @Test
    void discardTask_ShouldSetTaskStatusToDiscardedAndUpdateTask() {
        // Arrange
        Task task = createMockTask3();

        // Act
        Task discardedTask = taskService.discardTask(task);

        // Assert
        assertEquals(TaskStatus.DISCARDED, discardedTask.getTaskStatus());
        verify(taskRepository, times(1)).update(task);
    }
}
