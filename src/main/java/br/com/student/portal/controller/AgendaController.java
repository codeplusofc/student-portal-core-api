package br.com.student.portal.controller;

import br.com.student.portal.entity.AgendaEntity;
import br.com.student.portal.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    //TODO: CRIAR PAUTA
    //TODO: BUSCAR PAUTAS
    //TODO: BUSCAR PAUTA POR ID
    //TODO: INSERIR SESSÃO EM UMA PAUTA

    @Autowired
    private AgendaService agendaService;

    @PostMapping
    public AgendaEntity insertSession(AgendaEntity agendaEntity) {
        return agendaService.insertSession(agendaEntity);
    }
}
