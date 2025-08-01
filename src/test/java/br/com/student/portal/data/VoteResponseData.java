package br.com.student.portal.data;

import br.com.student.portal.dto.vote.VoteResponse;

import static br.com.student.portal.data.FixedData.AGENDA_ID;
import static br.com.student.portal.data.FixedData.USER_ID;
import static br.com.student.portal.data.FixedData.VOTE_ID;

public class VoteResponseData {
    public static VoteResponse generateVoteResponse(){
        var voteResponse = new VoteResponse();
        voteResponse.setVote(true);
        voteResponse.setId(VOTE_ID);
        voteResponse.setAgendaId(AGENDA_ID);
        voteResponse.setUserId(USER_ID);
        return voteResponse;
    }
}
