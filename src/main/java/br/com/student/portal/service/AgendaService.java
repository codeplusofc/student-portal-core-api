package br.com.student.portal.service;

import br.com.student.portal.entity.AgendaEntity;
import br.com.student.portal.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    public AgendaEntity insertSession(AgendaEntity agendaEntity) {
        var agendaResponse = agendaRepository.findById(agendaEntity.getId());

        if (agendaResponse.isEmpty()) {
            throw new RuntimeException("Agenda not found");
        }

        if (agendaResponse.get().getDeadline() == null && agendaEntity.getDeadline() != null) {

            agendaResponse.get().setDeadline(agendaEntity.getDeadline());
            return agendaRepository.save(agendaResponse.get());
        }
        throw new RuntimeException();
    }

    public AgendaEntity createAgenda(AgendaEntity agendaEntity) {
        return agendaRepository.save(agendaEntity);
    }

    public List<AgendaEntity> agendaFindAll() {
        return agendaRepository.findAll();
    }

    public Optional<AgendaEntity> agendaFindById(UUID id) {
        return agendaRepository.findById(id);
    }
}
