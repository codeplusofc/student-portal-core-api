package br.com.student.portal.service;

import br.com.student.portal.entity.TaskEntity;
import br.com.student.portal.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    public Optional<TaskEntity> findTaskById(UUID id){
        return taskRepository.findById(id);

    }
    public TaskEntity taskCreate(TaskEntity taskEntity){
        return taskRepository.save(taskEntity);
    }
    public void taskDelete(UUID id){
        taskRepository.deleteById(id);
    }
}
