package com.sheridancollege.taskmanager.service;

import com.sheridancollege.taskmanager.model.Task;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskService {

    private final List<Task> taskList = new ArrayList<>();
    private final AtomicInteger counter = new AtomicInteger(1);

    @PreAuthorize("hasRole('ADMIN')")
    public List<Task> getAllTasks() {
        return taskList;
    }

    @PreAuthorize("hasRole('USER')")
    public List<Task> getTasksForUser(String username) {
        List<Task> userTasks = new ArrayList<>();

        for (Task task : taskList) {
            if (task.getOwnerUsername().equals(username)) {
                userTasks.add(task);
            }
        }

        return userTasks;
    }

    @PreAuthorize("hasRole('USER')")
    public void addTask(String title, String description, String username) {
        Task task = new Task(counter.getAndIncrement(), title, description, false, username);
        taskList.add(task);
    }

    @PreAuthorize("hasRole('USER')")
    public void completeTask(Integer id, String username) {
        for (Task task : taskList) {
            if (task.getId().equals(id) && task.getOwnerUsername().equals(username)) {
                task.setCompleted(true);
                break;
            }
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTask(Integer id) {
        taskList.removeIf(task -> task.getId().equals(id));
    }
}