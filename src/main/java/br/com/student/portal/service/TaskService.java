package br.com.student.portal.service;

import br.com.student.portal.entity.TaskEntity;
import br.com.student.portal.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static br.com.student.portal.validation.TaskValidator.validateTaskFields;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Optional<TaskEntity> findTaskById(UUID id) {
        //TODO: Ao buscar uma tarefa inexistente devemos retornar uma mensagem de erro
        return taskRepository.findById(id);

    }

    public TaskEntity createTask(TaskEntity taskEntity) {

        validateTaskFields(taskEntity);

        return taskRepository.save(taskEntity);
    }

    public void taskDelete(UUID id) {
        //TODO: Ao deletar uma tarefa inexistente devemos retornar uma mensagem de erro
        taskRepository.deleteById(id);
    }

}
