package br.com.student.portal.controller;

import br.com.student.portal.dto.vote.VoteRequest;
import br.com.student.portal.dto.vote.VoteResponse;
import br.com.student.portal.entity.VoteEntity;
import br.com.student.portal.service.VoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/vote")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }


    @PostMapping("/create")
    public VoteResponse createVote(@RequestBody VoteRequest voteRequest) {
        return voteService.createVote(voteRequest);

    }

    @GetMapping
    public List<VoteEntity> getAllVotes() {
        return voteService.getAllVotes();
    }

    @GetMapping("/{id}")
    public Optional<VoteEntity> findVoteById(@PathVariable UUID id) {
        return voteService.getVoteById(id);
    }
}
