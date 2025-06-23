package br.com.student.portal.service;

import br.com.student.portal.entity.AgendaEntity;
import br.com.student.portal.exception.BadRequestException;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.AgendaRepository;
import br.com.student.portal.validation.AgendaValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;
    private AgendaValidator agendaValidator;

    public AgendaService(AgendaValidator agendaValidator) {
        this.agendaValidator = agendaValidator;
    }

    public AgendaEntity insertSession(AgendaEntity agendaEntity) {
        var agendaResponse = agendaRepository.findById(agendaEntity.getId());

        if (agendaResponse.isEmpty()) {

            throw new ObjectNotFoundException("Agenda com ID " + agendaEntity.getId() + " não encontrada.");
        }

        var agenda = agendaResponse.get();
        isDeadLineUpdateNeeded(agenda, agendaEntity);



        throw new BadRequestException("A data limite já está definida ou nenhuma alteração foi necessária.");
    }

    public AgendaEntity createAgenda(AgendaEntity agendaEntity) {
        AgendaValidator.validateNameIfExists(agendaEntity.getName(), agendaRepository);
        return agendaRepository.save(agendaEntity);
    }

    public List<AgendaEntity> agendaFindAll() {
        agendaValidator.validateAgendaR(agendaRepository);
        return agendaRepository.findAll();
    }

    public Optional<AgendaEntity> agendaFindById(UUID id) {
        var agendaOptional = agendaRepository.findById(id);

        if (agendaOptional.isEmpty()){
            throw new ObjectNotFoundException("Agenda not found");
        }

        return agendaRepository.findById(id);
    }
    private AgendaEntity isDeadLineUpdateNeeded(AgendaEntity agendaResponse, AgendaEntity agendaEntity){
        if (agendaResponse.getDeadline() == null && agendaEntity.getDeadline() != null){
            agendaResponse.setDeadline(agendaEntity.getDeadline());
            return agendaRepository.save(agendaResponse);
        }else{
            throw new BadRequestException("Something went wrong");
        }
    }

}
