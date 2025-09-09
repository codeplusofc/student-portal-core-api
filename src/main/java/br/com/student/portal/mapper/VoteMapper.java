package br.com.student.portal.mapper;

import br.com.student.portal.dto.vote.VoteRequest;
import br.com.student.portal.dto.vote.VoteResponse;
import br.com.student.portal.entity.VoteEntity;
import org.springframework.stereotype.Component;

@Component
public class VoteMapper {

    public VoteEntity voteRequestIntoVoteEntity(VoteRequest voteRequest){
        return new VoteEntity(voteRequest.isVote(),
                voteRequest.getUserId(),
                voteRequest.getAgendaId());
    }

    public VoteResponse voteEntityIntoVoteResponse(VoteEntity voteEntity){
        return new VoteResponse(voteEntity.getId(),
                voteEntity.getAgendaId(),
                voteEntity.getUserId());

    }
}
