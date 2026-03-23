package com.sheridancollege.taskmanager.controller;

import com.sheridancollege.taskmanager.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final TaskService taskService;

    public UserController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String userTasks(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("tasks", taskService.getTasksForUser(username));
        return "user-tasks";
    }

    @PostMapping("/add")
    public String addTask(@RequestParam String title,
                          @RequestParam String description,
                          Authentication authentication) {
        String username = authentication.getName();
        taskService.addTask(title, description, username);
        return "redirect:/user/tasks";
    }

    @PostMapping("/complete/{id}")
    public String completeTask(@PathVariable Integer id, Authentication authentication) {
        String username = authentication.getName();
        taskService.completeTask(id, username);
        return "redirect:/user/tasks";
    }
}