package br.com.student.portal.validation;

import br.com.student.portal.exception.BadRequestException;
import br.com.student.portal.repository.AgendaRepository;
import br.com.student.portal.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component


public class AgendaValidator {
    private AgendaRepository agendaRepository;

    public AgendaValidator(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public static void validateAgendaR(AgendaRepository agendaRepository){
        if(agendaRepository.findAll().isEmpty()){
            throw new BadRequestException("There's no agenda");
        }
    }
    public static void validateNameIfExists(String name, AgendaRepository agendaRepository){
        if(agendaRepository.existsByName(name)){
            throw new RuntimeException("There's another agenda with this name");
        }
    }
}
