package br.com.student.portal.controller;

import br.com.student.portal.entity.AgendaEntity;
import br.com.student.portal.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @PostMapping("/session")
    public ResponseEntity<AgendaEntity> insertSession(@RequestBody AgendaEntity agendaEntity) {
        var session = agendaService.insertSession(agendaEntity);
        return ResponseEntity.status(CREATED).body(session);


    }

    @PostMapping
    public ResponseEntity<AgendaEntity> createAgenda(@RequestBody AgendaEntity agendaEntity) {
        var agenda = agendaService.createAgenda(agendaEntity);
        return ResponseEntity.status(CREATED).body(agenda);
    }

    @GetMapping
    public ResponseEntity<List<AgendaEntity>> agendaFindAll() {
        var agenda = agendaService.agendaFindAll();
        return ResponseEntity.status(OK).body(agenda);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AgendaEntity>> agendaFindById(@PathVariable UUID id) {
        var agenda = agendaService.agendaFindById(id);
        return ResponseEntity.status(FOUND).body(agenda);
            }
}


