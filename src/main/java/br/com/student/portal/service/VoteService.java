package br.com.student.portal.service;

import br.com.student.portal.dto.vote.VoteRequest;
import br.com.student.portal.dto.vote.VoteResponse;
import br.com.student.portal.entity.VoteEntity;
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

        //TODO: Migrar para novas funções essa regra de negócio dos ifs
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
        //TODO: Ao não ser encontrado todos os votos, devemos lançar uma mensagem de erro
        return voteRepository.findAll();
    }

    public Optional<VoteEntity> getVoteById(UUID id) {
        //TODO: Retornar mensagem de erro ao não encontrar voto
        return voteRepository.findById(id);
    }
}

