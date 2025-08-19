package br.com.student.portal.service;

import br.com.student.portal.entity.AgendaEntity;
import br.com.student.portal.exception.BadRequestException;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.student.portal.validation.AgendaValidator.validateName;
import static java.time.LocalDateTime.now;


@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;


    public AgendaEntity insertSession(AgendaEntity agendaEntity) {
        var agendaResponse = agendaRepository.findById(agendaEntity.getId());

        if (agendaResponse.isEmpty()) {
            throw new ObjectNotFoundException("Agenda com ID " + agendaEntity.getId() + " não encontrada.");
        }

        var agenda = agendaResponse.get();
        isDeadLineUpdateNeeded(agenda, agendaEntity);
        return agendaRepository.save(agendaResponse.get());
    }

    public AgendaEntity createAgenda(AgendaEntity agendaEntity) {
        verifyAgendaFields(agendaEntity);
        return agendaRepository.save(agendaEntity);
    }

    public List<AgendaEntity> agendaFindAll() {
        var agendaResponse = agendaRepository.findAll();
        if (agendaResponse.isEmpty()) {
            throw new ObjectNotFoundException("There's no agenda in the repository");
        }
        return agendaResponse;
    }

    public Optional<AgendaEntity> agendaFindById(UUID id) {
        var agendaOptional = agendaRepository.findById(id);

        if (agendaOptional.isEmpty()) {
            throw new ObjectNotFoundException("Agenda not found");
        }

        return agendaRepository.findById(id);
    }

    public AgendaEntity isDeadLineUpdateNeeded(AgendaEntity agenda, AgendaEntity agendaEntity) {

        if (agendaEntity.getDeadline() != null &&
                !agenda.getDeadline().equals(agendaEntity.getDeadline())) {

            agenda.setDeadline(agendaEntity.getDeadline());

            return agendaRepository.save(agenda);
        } else {
            var defaultDeadLine = now().plusDays(7);
            agenda.setDeadline(defaultDeadLine);
            return agendaRepository.save(agenda);
        }
    }

    public void verifyAgendaFields(AgendaEntity agendaEntity) {
        validateName(agendaEntity.getName());
        verifyNameIfExists(agendaEntity.getName());

    }

    public void verifyNameIfExists(String name) {
        if (agendaRepository.existsByName(name)) {
            throw new BadRequestException("There's another agenda with this name");
        }
    }
}

