package br.com.student.portal.controller;

import br.com.student.portal.entity.TaskEntity;
import br.com.student.portal.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<TaskEntity>> findTasksByStudent(@PathVariable UUID studentId) {
        var tasks = taskService.findTasksByStudent(studentId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/create")
    public ResponseEntity<TaskEntity> createTask(@RequestBody TaskEntity taskEntity) {
        var task = taskService.createTask(taskEntity);
        return ResponseEntity.status(CREATED).body(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> taskDelete(@PathVariable UUID id) {
        taskService.taskDelete(id);
        return ResponseEntity.noContent().build();
    }
}
