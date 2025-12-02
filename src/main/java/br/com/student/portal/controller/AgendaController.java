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

@CrossOrigin
@RestController
@RequestMapping("/api/agendas")
public class AgendaController {
    @Autowired
    private AgendaService agendaService;

    @PostMapping("/session")
    public ResponseEntity<AgendaEntity> insertSession(@RequestBody AgendaEntity agendaEntity) {
        return ResponseEntity.status(CREATED).body(agendaService.insertSession(agendaEntity));
    }

    @PostMapping
    public ResponseEntity<AgendaEntity> createAgenda(@RequestBody AgendaEntity agendaEntity) {
        return ResponseEntity.status(CREATED).body(agendaService.createAgenda(agendaEntity));
    }

    @GetMapping
    public ResponseEntity<List<AgendaEntity>> agendaFindAll() {
        return ResponseEntity.status(OK).body(agendaService.agendaFindAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AgendaEntity>> agendaFindById(@PathVariable UUID id) {
        return ResponseEntity.status(FOUND).body(agendaService.agendaFindById(id));
    }
}


