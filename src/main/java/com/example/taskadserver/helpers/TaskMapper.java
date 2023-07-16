package com.example.taskadserver.helpers;

import com.example.taskadserver.dto.TaskResponse;
import com.example.taskadserver.dto.TaskSaveRequest;
import com.example.taskadserver.models.Task;
import com.example.taskadserver.models.User;
import com.example.taskadserver.services.contracts.TaskService;
import com.example.taskadserver.services.contracts.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskMapper {

    private final UserService userService;
    private final TaskService taskService;


    public TaskMapper(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }


    public Task convertToTask(TaskSaveRequest taskSaveRequest) {
        Task task = new Task();
        User user = userService.getUserByUsername(taskSaveRequest.getUser().getUsername());
        task.setUser(user);
        task.setTitle(taskSaveRequest.getTitle());
        task.setTaskDescription(taskSaveRequest.getTaskDescription());
        task.setDueDate(taskSaveRequest.getDueDate());
        task.setTaskStatus(taskSaveRequest.getTaskStatus());
        return task;
    }

    public TaskResponse convertToTaskResponse(Task task) {

        TaskResponse taskResponse = new TaskResponse();

        taskResponse.setTitle(task.getTitle());
        taskResponse.setDescription(task.getTaskDescription());
        taskResponse.setUsername(task.getUser().getUsername());
        taskResponse.setDueDate(task.getDueDate());
        taskResponse.setTaskStatus(task.getTaskStatus());
        return taskResponse;
    }

    public List<TaskResponse> convertToTaskResponses(List<Task> tasks) {

        List<TaskResponse> taskResponses = new ArrayList<>();

        tasks.forEach(task -> taskResponses.add(convertToTaskResponse(task)));
        return taskResponses;
    }
}
