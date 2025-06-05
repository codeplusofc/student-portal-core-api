package br.com.student.portal.service;

import br.com.student.portal.entity.AgendaEntity;
import br.com.student.portal.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    public AgendaEntity insertSession(AgendaEntity agendaEntity) {
        var agendaResponse = agendaRepository.findById(agendaEntity.getId());

        if (agendaResponse.isPresent()) {
            if (agendaResponse.get().getDeadline() == null) {
                if (agendaEntity.getDeadline() != null) {
                    agendaResponse.get().setDeadline(agendaEntity.getDeadline());
                    return agendaRepository.save(agendaResponse.get());
                }
                //TODO: CRIAR UM PRAZO DEFAULT CASO NÃO SEJA INSERIDO!
            }
        }
        throw new RuntimeException("Agenda not found");
    }
}

//Se a pauta no banco já estiver com prazo, utilizamos ele
//Se não estiver com prazo, criamos um prazo por padrão
