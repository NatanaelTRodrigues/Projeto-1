package com.example.demo.dto;

import com.example.demo.model.Task;

public class TaskMapper {

    
    public static TaskResponse toResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setCompleted(task.isCompleted());
        response.setDueDate(task.getDueDate());

        // Adiciona o ID do usuário para referência no cliente
        response.setUserId(task.getUser().getId()); 
        return response;
    }

    
    public static Task toEntity(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        // Nota: O campo 'user' será preenchido no Service/Controller
        return task;
    }
}