package br.com.student.portal.service;

import br.com.student.portal.entity.TaskEntity;
import br.com.student.portal.repository.TaskRepository;
import br.com.student.portal.validation.TaskValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskService {
    private TaskValidator taskValidator;
    private TaskRepository taskRepository;
    public Optional<TaskEntity> findTaskById(UUID id){
        return taskRepository.findById(id);

    }
    public TaskEntity createTask(TaskEntity taskEntity){
        taskValidator.validateName(taskEntity.getName());
        taskValidator.validateDeadLine(taskEntity.getDeadline());
        taskValidator.validateDescription(taskEntity.getDescription());
        taskValidator.validateTitle(taskEntity.getTitle());
        taskValidator.validateCourse(taskEntity.getCourse());
        return taskRepository.save(taskEntity);
    }
    public void taskDelete(UUID id){
        taskRepository.deleteById(id);
    }

}
