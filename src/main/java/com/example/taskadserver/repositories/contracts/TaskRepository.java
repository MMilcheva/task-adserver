package com.example.taskadserver.repositories.contracts;


import com.example.taskadserver.models.Task;
import com.example.taskadserver.models.TaskFilterOptions;
import com.example.taskadserver.models.enums.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends BaseCRUDRepository<Task> {

    List<Task> getAllTasksFilter(TaskFilterOptions taskFilterOptions);


    List<Task> filter(
            Optional<Long> userId,
            Optional<String> title,
            Optional<String> taskDescription,
            Optional<TaskStatus> status,
            Optional<String> sortBy,
            Optional<String> sortOrder);
}
