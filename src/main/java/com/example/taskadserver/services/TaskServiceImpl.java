package com.example.taskadserver.services;

import com.example.taskadserver.models.Task;
import com.example.taskadserver.models.TaskFilterOptions;
import com.example.taskadserver.models.enums.TaskStatus;
import com.example.taskadserver.repositories.contracts.TaskRepository;
import com.example.taskadserver.services.contracts.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.getById(taskId);
    }

    @Override
    public List<Task> getAllTasksFilter(TaskFilterOptions taskFilterOptions) {
        return taskRepository.getAllTasksFilter(taskFilterOptions);
    }

    @Override
    public Task createTask(Task task) {
        task.setTaskStatus(TaskStatus.PENDING);
        taskRepository.create(task);
        return task;
    }

    @Override
    public Task updateTask(Task task) {
        if (task.getTaskStatus() == TaskStatus.COMPLETED || task.getTaskStatus() == TaskStatus.DISCARDED) {
            task.setDueDate(LocalDate.now());
        }
        taskRepository.update(task);
        return task;
    }

    @Override
    public Task completeTask(Task task) {
        task.setTaskStatus(TaskStatus.COMPLETED);
        return updateTask(task);
    }

    @Override
    public Task discardTask(Task task) {
        task.setTaskStatus(TaskStatus.DISCARDED);
        return updateTask(task);
    }
}
