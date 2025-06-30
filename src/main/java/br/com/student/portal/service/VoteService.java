package br.com.student.portal.service;

import br.com.student.portal.dto.vote.AgendaResultDTO;
import br.com.student.portal.dto.vote.VoteRequest;
import br.com.student.portal.dto.vote.VoteResponse;
import br.com.student.portal.entity.VoteEntity;
import br.com.student.portal.exception.BadRequestException;
import br.com.student.portal.exception.ForbiddenException;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.AgendaRepository;
import br.com.student.portal.repository.UserRepository;
import br.com.student.portal.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final AgendaRepository agendaRepository;
    private final UserRepository userRepository;

    public VoteResponse createVote(VoteRequest voteRequest) {
        var voteEntity = new VoteEntity(voteRequest.getAgendaId(),
                voteRequest.getUserId(),
                voteRequest.isVote());

        var date = LocalDateTime.now();

        var responseAgenda = agendaRepository.findById(voteEntity.getAgendaId());
        var userExists = userRepository.existsById(voteEntity.getUserId());


        if (responseAgenda.isPresent() && userExists) {
            var responseVote = voteRepository.findByUserIdAndAgendaId(voteEntity.getUserId(),
                    voteEntity.getAgendaId());

            if (responseVote.isEmpty()) {
                var agenda = responseAgenda.orElseThrow(() -> new ObjectNotFoundException("Agenda not found"));

                if (date.isBefore(agenda.getDeadline())) {
                    var voteSaved = voteRepository.save(voteEntity);
                    return new VoteResponse(voteSaved.getId(),
                            voteSaved.isVote(),
                            voteSaved.getUserId(),
                            voteSaved.getAgendaId());
                }

            }
            throw new ForbiddenException("You already voted");
        }
        throw new ObjectNotFoundException("User or agenda not found");
    }

    public List<VoteEntity> getAllVotes() {

        return voteRepository.findAll();
    }

    public Optional<VoteEntity> getVoteById(UUID id) {
        return voteRepository.findById(id);
    }
    public AgendaResultDTO getAgendaResult(UUID id){
        var agenda = agendaRepository.findById(id);
        var votes = voteRepository.findByAgendaId(agenda.get().getId());
        if(LocalDateTime.now().isBefore(agenda.get().getDeadline())){
            throw new BadRequestException("This agenda is not over");
        }

        int yes = 0;
        int no = 0;
        String result = "result";

        for(int counter = 0; counter<votes.size();counter++){
            if(votes.get(counter).isVote()){
                yes++;
            }else{
                no++;
            }
        }
        if(yes > no){
            result = "Agenda approved";
        }else{
            result = "Agenda declined";
        }
        return new AgendaResultDTO(id, yes, no, result);
    }

}

