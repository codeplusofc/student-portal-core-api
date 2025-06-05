package br.com.student.portal.controller;

import br.com.student.portal.entity.TaskEntity;
import br.com.student.portal.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/Tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public Optional<TaskEntity> findTaskById(@PathVariable UUID id) {
        return taskService.findTaskById(id);
    }

    @PostMapping("/create")
    public TaskEntity createTask(@RequestBody TaskEntity taskEntity) {
        return taskService.createTask(taskEntity);
    }

    @DeleteMapping("/{id}")
    public void taskDelete(@PathVariable UUID id) {
        taskService.taskDelete(id);
    }
}


//
