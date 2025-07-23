package br.com.student.portal.service;

import br.com.student.portal.dto.vote.AgendaResultDTO;
import br.com.student.portal.dto.vote.VoteRequest;
import br.com.student.portal.dto.vote.VoteResponse;
import br.com.student.portal.entity.AgendaEntity;
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

        var agenda = validateUserAndAgendaExists(voteEntity.getUserId(), voteEntity.getAgendaId());

        checkIfUserHasAlreadyVoted(voteEntity.getUserId(), voteEntity.getAgendaId());


        checkAgendaIsOpen(agenda);
        var savedVote = voteRepository.save(voteEntity);
        return new VoteResponse(savedVote.getId(),
                savedVote.isVote(),
                savedVote.getUserId(),
                savedVote.getAgendaId());
    }

    public List<VoteEntity> getAllVotes() {
        return voteRepository.findAll();
    }

    public Optional<VoteEntity> getVoteById(UUID id) {
        return voteRepository.findById(id);
    }

    public AgendaResultDTO getAgendaResult(UUID id) {
        var agenda = agendaRepository.findById(id);
        var votes = voteRepository.findByAgendaId(agenda.get().getId());


        if (LocalDateTime.now().isBefore(agenda.get().getDeadline())) {
            throw new BadRequestException("This agenda is not over");
        }

        return calculateAgendaResult(agenda.get().getId(), votes);

    }

    public AgendaEntity validateUserAndAgendaExists(UUID userId, UUID agendaId) {
        var userExists = userRepository.findById(userId);
        var agenda = agendaRepository.findById(agendaId);
        if (userExists.isEmpty() || agenda.isEmpty()) {
            throw new ObjectNotFoundException("User or agenda not found");
        }
        return agenda.get();
    }


    public void checkIfUserHasAlreadyVoted(UUID userId, UUID agendaId) {
        boolean alreadyVoted = voteRepository.findByUserIdAndAgendaId(userId, agendaId).isPresent();
        if (alreadyVoted) {
            throw new ForbiddenException("You already voted");
        }

    }

    public void checkAgendaIsOpen(AgendaEntity agendaEntity) {
        if (LocalDateTime.now().isAfter(agendaEntity.getDeadline())) {
            throw new ForbiddenException("This agenda is already closed");
        }
    }

    public AgendaResultDTO calculateAgendaResult(UUID agendaId, List<VoteEntity> votes) {
        long yesVotes = votes.stream().filter(VoteEntity::isVote).count();
        long noVotes = votes.size() - yesVotes;
        String result = "result";

        if (yesVotes > noVotes) {
            result = "Approved";
        } else {
            result = "Denied";
        }
        return new AgendaResultDTO(agendaId, yesVotes, noVotes, result);

}
}


