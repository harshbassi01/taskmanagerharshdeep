package com.sheridancollege.taskmanager.controller;

import com.sheridancollege.taskmanager.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final TaskService taskService;

    public AdminController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String adminTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "admin-tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return "redirect:/admin/tasks";
    }
}