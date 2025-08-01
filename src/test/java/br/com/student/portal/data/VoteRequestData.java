package br.com.student.portal.data;

import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.vote.VoteRequest;

import static br.com.student.portal.data.FixedData.AGENDA_ID;
import static br.com.student.portal.data.FixedData.USER_ID;


public class VoteRequestData {
        public static VoteRequest generateVoteRequest(){
            var voteRequest = new VoteRequest();
            voteRequest.setVote(true);
            voteRequest.setAgendaId(AGENDA_ID);
            voteRequest.setUserId(USER_ID);
            return voteRequest;
        }

    }



