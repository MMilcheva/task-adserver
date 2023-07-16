package com.example.taskadserver.services.contracts;

import com.example.taskadserver.models.Task;
import com.example.taskadserver.models.TaskFilterOptions;

import java.util.List;

public interface TaskService {


    Task getTaskById(Long taskId);


    List<Task> getAllTasksFilter(TaskFilterOptions taskFilterOptions);

    Task createTask(Task task);

    Task updateTask(Task task);


    Task completeTask(Task task);


    Task discardTask(Task task);
}
