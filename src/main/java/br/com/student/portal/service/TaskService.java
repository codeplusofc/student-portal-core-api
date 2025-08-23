package br.com.student.portal.service;

import br.com.student.portal.entity.TaskEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static br.com.student.portal.validation.TaskValidator.validateTaskFields;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskEntity> findTasksByStudent(UUID studentId) {
        var tasks = taskRepository.findByStudentId(studentId);

        if (tasks.isEmpty()) {
            throw new ObjectNotFoundException("This student has no tasks registered.");
        }

        return tasks;

    }

    public TaskEntity createTask(TaskEntity taskEntity) {

        validateTaskFields(taskEntity);

        return taskRepository.save(taskEntity);
    }

    public void taskDelete(UUID id) {
        if (taskRepository.findById(id).isEmpty()) {
            throw new ObjectNotFoundException("There's no task with this id");
        }
        taskRepository.deleteById(id);
    }

}
