package br.com.student.portal.controller;

import br.com.student.portal.dto.vote.AgendaResultDTO;
import br.com.student.portal.dto.vote.VoteRequest;
import br.com.student.portal.dto.vote.VoteResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.entity.VoteEntity;
import br.com.student.portal.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;

@AllArgsConstructor
@RestController
@RequestMapping("/vote")
public class VoteController {

    private final VoteService voteService;

    @Operation(summary = "Create Vote")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vote registered with sucessful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VoteEntity.class))),
            @ApiResponse(responseCode = "400", description = "Vote already exists")})


    @PostMapping("/create")
    public ResponseEntity<VoteResponse> createVote(@RequestBody VoteRequest voteRequest) {
        return ResponseEntity.status(CREATED).body(voteService.createVote(voteRequest));
    }

    @GetMapping
    public ResponseEntity<List<VoteResponse>> getAllVotes() {
        return ResponseEntity.status(FOUND).body(voteService.getAllVotes());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<VoteEntity>> findVoteById(@PathVariable UUID id) {
        return ResponseEntity.status(FOUND).body(voteService.getVoteById(id));
    }

    @GetMapping("/result/{id}")
    public ResponseEntity<AgendaResultDTO> getAgendaResult(@PathVariable UUID id) {
        return ResponseEntity.status(FOUND).body(voteService.getAgendaResult(id));
    }
}
