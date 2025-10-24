package com.example.demo.controller;

import com.example.demo.dto.TaskMapper;
import com.example.demo.dto.TaskRequest;
import com.example.demo.dto.TaskResponse;
import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    
    @PostMapping("/{userId}")
    public ResponseEntity<TaskResponse> createTask(@PathVariable Long userId, @RequestBody TaskRequest request) {
        Task taskEntity = TaskMapper.toEntity(request);
        Task createdTask = taskService.createTask(userId, taskEntity);

        return new ResponseEntity<>(TaskMapper.toResponse(createdTask), HttpStatus.CREATED);
    }

    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponse>> getTasksByUserId(@PathVariable Long userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);

        List<TaskResponse> responses = tasks.stream()
                .map(TaskMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }
}