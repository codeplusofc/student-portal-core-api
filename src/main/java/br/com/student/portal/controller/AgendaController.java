package br.com.student.portal.controller;

import br.com.student.portal.entity.AgendaEntity;
import br.com.student.portal.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/agenda")
public class AgendaController {





    @Autowired
    private AgendaService agendaService;

    @PostMapping("/session")
    public AgendaEntity insertSession(@RequestBody AgendaEntity agendaEntity) {
        return agendaService.insertSession(agendaEntity);
    }

    @PostMapping
    public AgendaEntity createAgenda(@RequestBody AgendaEntity agendaEntity){
        return agendaService.createAgenda(agendaEntity);

    }
    @GetMapping
    public List<AgendaEntity> agendaFindAll(AgendaEntity agendaEntity){
        return agendaService.agendaFindAll(agendaEntity);
    }
    @GetMapping("/{id}")
    public Optional<AgendaEntity> agendaFindById(@PathVariable UUID id){
        return agendaService.agendaFindById(id);
    }
}
