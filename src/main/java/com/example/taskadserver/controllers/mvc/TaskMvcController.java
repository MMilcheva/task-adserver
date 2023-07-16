package com.example.taskadserver.controllers.mvc;

import com.example.taskadserver.dto.TaskFilterDto;
import com.example.taskadserver.dto.TaskResponse;
import com.example.taskadserver.dto.TaskSaveRequest;
import com.example.taskadserver.exceptions.AuthenticationFailureException;
import com.example.taskadserver.exceptions.DuplicateEntityException;
import com.example.taskadserver.exceptions.EntityNotFoundException;
import com.example.taskadserver.exceptions.UnauthorizedOperationException;
import com.example.taskadserver.helpers.AuthenticationHelper;
import com.example.taskadserver.helpers.TaskMapper;
import com.example.taskadserver.models.Task;
import com.example.taskadserver.models.TaskFilterOptions;
import com.example.taskadserver.models.User;
import com.example.taskadserver.services.contracts.TaskService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * The TaskMvcController gets the user requests from the view layer and processes them,
 * with the necessary validations. It acts as an interface between Model and View.
 * The requests are then sent to model for data processing. Once they are processed,
 * the data is sent back to the controller and then displayed on the view.
 */
@Controller
@RequestMapping("/tasks")
public class TaskMvcController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public TaskMvcController(TaskService taskService, TaskMapper taskMapper,
                             AuthenticationHelper authenticationHelper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.authenticationHelper = authenticationHelper;
    }

    /**
     * @param session is a conversional state between client and server, and it can consist of multiple requests and
     *                responses between client and server. Since HTTP and Web Server both are stateless,
     *                the only way to maintain a session is when some unique information about the session (session id)
     *                is passed between server and client in every request and response.
     * @return if the user is authenticated or not
     */
    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    /**
     * @param taskId Unique id
     * @param model  represents of the data being posted to the Controller,
     *               the data being worked on in a View or the representation of the domain specific entities
     *               operating in the business tier.
     * @return shows the details of a single task, with the mentioned id.
     */
    @GetMapping("{taskId}")
    public String showSingleTask(@PathVariable Long taskId, Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        Task task;
        try {
            task = taskService.getTaskById(taskId);
            model.addAttribute("task", task);
            return "TaskView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (UnauthorizedOperationException e) {
            return "NotFoundView";
        }
    }

    /**
     * @param taskFilterDto is an abbreviation for Data Transfer Object, so it is used to transfer the data
     *                      between classes and modules of the application.
     * @param model         represents of the data being posted to the Controller,
     *                      the data being worked on in a View or the representation of the domain specific entities
     *                      operating in the business tier.
     * @param session       is a conversional state between client and server, and it can consist of multiple requests and
     *                      responses between client and server.
     * @return all tasks, filtered by TaskFilterDto filters.
     */
    @GetMapping
    public String filterAllTasks(@ModelAttribute("taskFilterOptions") TaskFilterDto taskFilterDto,
                                 Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        taskFilterDto.setUserId(user.getUserId());
        TaskFilterOptions taskFilterOptions = new TaskFilterOptions(
                taskFilterDto.getUserId(),
                taskFilterDto.getTitle(),
                taskFilterDto.getTaskDescription(),
                taskFilterDto.getTaskStatus(),
                taskFilterDto.getDueDate(),
                taskFilterDto.getSortBy(),
                taskFilterDto.getSortOrder());

        model.addAttribute("tasks", taskService.getAllTasksFilter(taskFilterOptions));
        model.addAttribute("taskFilterOptions", taskFilterDto);

        return "TasksView";
    }

    /**
     * @param taskFilterDto is an abbreviation for Data Transfer Object, so it is used to transfer the data
     *                      between classes and modules of the application.
     * @param model         represents of the data being posted to the Controller,
     *                      the data being worked on in a View or the representation of the domain specific entities
     *                      operating in the business tier.
     * @param session       is a conversional state between client and server, and it can consist of multiple requests and
     *                      responses between client and server.
     * @return all tasks, filtered by user. In case of exception, returns the respective page.
     */
    @GetMapping("/my-tasks")
    public String filterAllTasksByUser(@ModelAttribute("taskFilterOptions") TaskFilterDto taskFilterDto,
                                       Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        taskFilterDto.setUserId(user.getUserId());
        try {
            TaskFilterOptions taskFilterOptions = new TaskFilterOptions(
                    taskFilterDto.getUserId(),
                    taskFilterDto.getTitle(),
                    taskFilterDto.getTaskDescription(),
                    taskFilterDto.getTaskStatus(),
                    taskFilterDto.getDueDate(),
                    taskFilterDto.getSortBy(),
                    taskFilterDto.getSortOrder()
            );

            if (taskFilterOptions.getSortBy().isEmpty()) {
                taskFilterOptions.setSortBy(Optional.of("dueDate"));
                taskFilterOptions.setSortOrder(Optional.of("asc"));
            }

            List<Task> tasks = taskService.getAllTasksFilter(taskFilterOptions);
            taskMapper.convertToTaskResponses(tasks);

            model.addAttribute("tasks", tasks);
            return "TasksView";
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    /**
     * @param taskSaveRequest is a Data Transfer Object, used to transfer the user's input
     *                        to the classes and modules of the application.
     * @param model           represents of the data being posted to the Controller,
     *                        the data being worked on in a View or the representation of the domain specific entities
     *                        operating in the business tier.
     * @param session         is a conversional state between client and server, and it can consist of multiple requests and
     *                        responses between client and server.
     * @return Shows the Create task page where the user inputs their task related data. In case of exception, returns the respective page.
     */
    @GetMapping("/new")
    public String showCreateTaskPage(@ModelAttribute("taskSaveRequest") TaskSaveRequest taskSaveRequest,
                                     Model model,
                                     HttpSession session) {
        try {
            User user = authenticationHelper.tryGetUserWithSession(session);
            Task task = new Task();
            model.addAttribute("task", task);
            model.addAttribute("taskSaveRequest", taskSaveRequest);

            return "TaskCreateView";
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    /**
     * @param taskSaveRequest is a Data Transfer Object, used to transfer the user's input
     *                        to the classes and modules of the application.
     * @param bindingResult   holds the result of a validation and binding and contains errors that may have occurred
     * @param model           represents of the data being posted to the Controller,
     *                        the data being worked on in a View or the representation of the domain specific entities
     *                        operating in the business tier.
     * @param session         is a conversional state between client and server, and it can consist of multiple requests and
     *                        responses between client and server.
     * @return the newly created task. In case of exception, returns the respective page.
     */
    @PostMapping("/new")
    public String createTask(@Valid @ModelAttribute("taskSaveRequest") TaskSaveRequest taskSaveRequest,
                             BindingResult bindingResult,
                             Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);

        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            return "TaskCreateView";
        }
        try {
            taskSaveRequest.setUser(user);
            Task task = taskMapper.convertToTask(taskSaveRequest);
            Task createdTask = taskService.createTask(task);

            return "redirect:/tasks/" + createdTask.getTaskId();
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue("title", "duplicate_task", e.getMessage());
            return "TaskCreateView";
        }
    }

    /**
     * @param taskId  Unique id
     * @param model   represents of the data being posted to the Controller,
     *                the data being worked on in a View or the representation of the domain specific entities
     *                operating in the business tier.
     * @param session is a conversional state between client and server, and it can consist of multiple requests and
     *                responses between client and server.
     * @return Shows the Update task page where the user inputs their changes in the task data. In case of exception, returns the respective page.
     */
    @GetMapping("/{taskId}/update")
    public String showUpdateTaskPage(@PathVariable Long taskId, Model model, HttpSession session) {

        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        try {
            Task task = taskService.getTaskById(taskId);
            TaskResponse taskResponse = taskMapper.convertToTaskResponse(task);

            TaskSaveRequest taskSaveRequest = new TaskSaveRequest();

            taskSaveRequest.setUser(user);
            taskSaveRequest.setTitle(taskResponse.getTitle());
            taskSaveRequest.setTaskDescription(taskResponse.getDescription());
            taskSaveRequest.setDueDate(taskResponse.getDueDate());
            taskSaveRequest.setTaskStatus(taskResponse.getTaskStatus());
            model.addAttribute("taskId", taskId);
            model.addAttribute("taskSaveRequest", taskSaveRequest);
            return "TaskUpdateView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    /**
     * @param taskId          Unique id
     * @param taskSaveRequest is a Data Transfer Object, used to transfer the user's input
     *                        to the classes and modules of the application.
     * @param bindingResult   holds the result of a validation and binding and contains errors that may have occurred
     * @param model           represents of the data being posted to the Controller,
     *                        the data being worked on in a View or the representation of the domain specific entities
     *                        operating in the business tier.
     * @param session         is a conversional state between client and server, and it can consist of multiple requests and
     *                        responses between client and server.
     * @return Shows the Updated task. In case of exception, returns the respective page.
     */
    @PostMapping("/{taskId}/update")
    public String updateTask(@PathVariable Long taskId,
                             @Valid @ModelAttribute("taskSaveRequest") TaskSaveRequest taskSaveRequest,
                             BindingResult bindingResult,
                             Model model, HttpSession session) {
        User checkUser;
        try {
            checkUser = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        if (bindingResult.hasErrors()) {
            return "TaskUpdateView";
        }
        try {
            taskSaveRequest.setUser(checkUser);
            Task task = taskMapper.convertToTask(taskSaveRequest);

            Task taskToBeUpdated = taskService.getTaskById(taskId);

            taskToBeUpdated.setTitle(task.getTitle());
            taskToBeUpdated.setTaskDescription(task.getTaskDescription());
            taskToBeUpdated.setDueDate(task.getDueDate());
            taskToBeUpdated.setTaskStatus(task.getTaskStatus());

            taskService.updateTask(taskToBeUpdated);

            return "redirect:/tasks/{taskId}";

        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue("task", "duplicate_task", e.getMessage());
            return "TaskUpdateView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    /**
     * @param taskId  Unique id
     * @param model   represents of the data being posted to the Controller,
     *                the data being worked on in a View or the representation of the domain specific entities
     *                operating in the business tier.
     * @param session is a conversional state between client and server, and it can consist of multiple requests and
     *                responses between client and server.
     * @return Shows the task with updated to completed status. In case of exception, returns the respective page.
     */
    @GetMapping("/{taskId}/complete")
    public String completeTask(@PathVariable Long taskId, Model model, HttpSession session) {

        try {
            User user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        try {
            Task task = taskService.getTaskById(taskId);
            taskService.completeTask(task);
            return "redirect:/tasks/{taskId}";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }

    }

    /**
     * @param taskId  Unique id
     * @param model   represents of the data being posted to the Controller,
     *                the data being worked on in a View or the representation of the domain specific entities
     *                operating in the business tier.
     * @param session is a conversional state between client and server, and it can consist of multiple requests and
     *                responses between client and server.
     * @return Shows the task with updated to discarded status. In case of exception, returns the respective page.
     */
    @GetMapping("/{taskId}/discard")
    public String discardTask(@PathVariable Long taskId, Model model, HttpSession session) {

        try {
            User user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        try {
            Task task = taskService.getTaskById(taskId);
            taskService.discardTask(task);

            return "redirect:/tasks/{taskId}";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }
}