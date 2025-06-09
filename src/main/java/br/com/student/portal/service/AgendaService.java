package br.com.student.portal.service;

import br.com.student.portal.entity.AgendaEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.AgendaRepository;
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

    public AgendaEntity insertSession(AgendaEntity agendaEntity) {
        var agendaResponse = agendaRepository.findById(agendaEntity.getId());

        if (agendaResponse.isEmpty()) {
            //TODO: Ao lançar a exceção abaixo, devemos lançar um ObjectNotFoudException e não o EntityNotFoundException
            throw new EntityNotFoundException("Agenda com ID " + agendaEntity.getId() + " não encontrada.");
        }

        var agenda = agendaResponse.get();

        //TODO: Implementar a regra de negócio do if em uma outra função e chamar aqui dentro do insertSession
        if (agenda.getDeadline() == null && agendaEntity.getDeadline() != null) {

            agenda.setDeadline(agendaEntity.getDeadline());
            return agendaRepository.save(agenda);
        }
        //TODO: Abaixo alterar a exceção para BadRequest
        throw new RuntimeException("A data limite já está definida ou nenhuma alteração foi necessária.");
    }

    public AgendaEntity createAgenda(AgendaEntity agendaEntity) {
        //TODO: Validar se o nome da pauta está sendo inserido corretamente
        return agendaRepository.save(agendaEntity);
    }

    public List<AgendaEntity> agendaFindAll() {
        //TODO: Ao buscar uma lista e o retorno for vazio, devemos mostrar uma mensagem de erro
        return agendaRepository.findAll();
    }

    public Optional<AgendaEntity> agendaFindById(UUID id) {
        var agendaOptional = agendaRepository.findById(id);

        if (agendaOptional.isEmpty()){
            throw new ObjectNotFoundException("Agenda not found");
        }

        return agendaRepository.findById(id);
    }
}
