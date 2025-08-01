package br.com.student.portal.data;

import br.com.student.portal.entity.VoteEntity;

import static br.com.student.portal.data.FixedData.*;

public class VoteEntityData {

    public static VoteEntity generateVoteEntity() {
        var voteEntity = new VoteEntity();
        voteEntity.setVote(true);
        voteEntity.setId(VOTE_ID);
        voteEntity.setAgendaId(AGENDA_ID);
        voteEntity.setUserId(USER_ID);
        return voteEntity;
    }
}
