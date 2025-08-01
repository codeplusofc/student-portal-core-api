package br.com.student.portal.controller;

import br.com.student.portal.dto.vote.AgendaResultDTO;
import br.com.student.portal.dto.vote.VoteRequest;
import br.com.student.portal.dto.vote.VoteResponse;
import br.com.student.portal.entity.VoteEntity;
import br.com.student.portal.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;

@RestController
@RequestMapping("/vote")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }


    @PostMapping("/create")
    public ResponseEntity<VoteResponse> createVote(@RequestBody VoteRequest voteRequest) {
        var vote = voteService.createVote(voteRequest);
        return ResponseEntity.status(CREATED).body(vote);
    }

    @GetMapping
    public ResponseEntity<List<VoteResponse>> getAllVotes() {
        var vote = voteService.getAllVotes();
        return ResponseEntity.status(FOUND).body(vote);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<VoteEntity>> findVoteById(@PathVariable UUID id) {
        var vote = voteService.getVoteById(id);
        return ResponseEntity.status(FOUND).body(vote);
    }

    @GetMapping("/result/{id}")
    public ResponseEntity<AgendaResultDTO> getAgendaResult(@PathVariable UUID id) {
        var agendaResult = voteService.getAgendaResult(id);
        return ResponseEntity.status(FOUND).body(agendaResult);
    }
}
