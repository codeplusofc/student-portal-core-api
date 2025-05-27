package br.com.student.portal.service;

import br.com.student.portal.dto.vote.VoteRequest;
import br.com.student.portal.dto.vote.VoteResponse;
import br.com.student.portal.entity.VoteEntity;
import br.com.student.portal.repository.AgendaRepository;
import br.com.student.portal.repository.UserRepository;
import br.com.student.portal.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final AgendaRepository agendaRepository;
    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, AgendaRepository agendaRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.agendaRepository = agendaRepository;
        this.userRepository = userRepository;
    }

    public VoteResponse createVote(VoteRequest voteRequest) {
        var voteEntity = new VoteEntity(voteRequest.getAgendaId(),
                voteRequest.getUserId(),
                voteRequest.isVote());

        var date = LocalDateTime.now();

        var responseAgenda = agendaRepository.findById(voteEntity.getAgendaId());

        if (agendaRepository.existsById(voteEntity.getAgendaId()) && userRepository.existsById(voteEntity.getUserId())) {
            var responseVote = voteRepository.findByUserIdAndAgendaId(voteEntity.getUserId(),
                    voteEntity.getAgendaId());

            if (responseVote == null) {
                if (date.isBefore(responseAgenda.get().getDeadline())) {
                    var voteSaved = voteRepository.save(voteEntity);
                    return new VoteResponse(voteSaved.getId(),
                            voteSaved.isVote(),
                            voteSaved.getUserId(),
                            voteSaved.getAgendaId());
                }
                throw new RuntimeException("This agenda already ended");
            }
            throw new RuntimeException("You already voted");

        }
        throw new RuntimeException("User or agenda not found");
    }
    public List<VoteEntity> getAllVotes(){
        return voteRepository.findAll();
    }
    public Optional<VoteEntity> getVoteById(UUID id){
        return voteRepository.findById(id);
    }
}
