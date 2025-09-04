package br.com.student.portal.controller;

import br.com.student.portal.entity.TaskEntity;
import br.com.student.portal.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.apache.tomcat.websocket.Constants.FOUND;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TaskEntity>> findTaskById(@PathVariable UUID id) {
        return ResponseEntity.status(FOUND).body(taskService.findTaskById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<TaskEntity> createTask(@RequestBody TaskEntity taskEntity) {
        return ResponseEntity.status(CREATED).body(taskService.createTask(taskEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> taskDelete(@PathVariable UUID id) {
        taskService.taskDelete(id);
        return ResponseEntity.noContent().build();
    }
}
