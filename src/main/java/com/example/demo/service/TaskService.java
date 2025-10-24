package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository; 

    
    public Task createTask(Long userId, Task task) {
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for task creation"));

        task.setUser(user);
        return taskRepository.save(task);
    }

    
    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    // updateTask, deleteTask, getTaskById, etc.
}