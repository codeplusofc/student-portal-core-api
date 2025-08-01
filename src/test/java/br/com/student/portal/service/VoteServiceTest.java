package br.com.student.portal.service;


import br.com.student.portal.dto.vote.VoteResponse;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.mapper.VoteMapper;
import br.com.student.portal.repository.AgendaRepository;
import br.com.student.portal.repository.UserRepository;
import br.com.student.portal.repository.VoteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static br.com.student.portal.data.AgendaEntityData.generateAgendaEntity;
import static br.com.student.portal.data.FixedData.*;
import static br.com.student.portal.data.UserEntityData.generateUserEntity;
import static br.com.student.portal.data.VoteEntityData.generateVoteEntity;
import static br.com.student.portal.data.VoteRequestData.generateVoteRequest;
import static br.com.student.portal.data.VoteResponseData.generateVoteResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class VoteServiceTest {
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private AgendaRepository agendaRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private VoteMapper voteMapper;
    @InjectMocks
    private VoteService voteService;

    @Test
    public void mustCreateVote(){
        var voteRequest = generateVoteRequest();
        var voteEntity = generateVoteEntity();
        var agendaEntity = generateAgendaEntity();
        var userEntity = generateUserEntity();
        var voteResponse = generateVoteResponse();

        given(voteMapper.voteRequestIntoVoteEntity(voteRequest)).willReturn(voteEntity);
        given(userRepository.findById(USER_ID)).willReturn(Optional.of(userEntity));
        given(agendaRepository.findById(AGENDA_ID)).willReturn(Optional.of(agendaEntity));
        given(voteMapper.voteEntityIntoVoteResponse(voteEntity)).willReturn(voteResponse);
        given(voteRepository.save(voteEntity)).willReturn(voteEntity);

        var result = voteService.createVote(voteRequest);

        assertEquals(true, result.isVote());
        assertEquals(AGENDA_ID, result.getAgendaId());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(VOTE_ID, result.getId());


    }
    @Test
    public void mustGetAllVotes(){
        var voteOne = generateVoteEntity();
        var voteTwo = generateVoteEntity();
        var votes = List.of(voteOne, voteTwo);

        var responseOne = generateVoteResponse();
        var responseTwo = generateVoteResponse();
        var expectedResponses = List.of(responseOne, responseTwo);

        given(voteRepository.findAll()).willReturn(votes);
        given(voteMapper.voteEntityIntoVoteResponse(voteOne)).willReturn(responseOne);
        given(voteMapper.voteEntityIntoVoteResponse(voteTwo)).willReturn(responseTwo);

        List<VoteResponse> result = voteService.getAllVotes();

        assertEquals(expectedResponses, result);


    }
    @Test
    public void mustNotFindVotes(){
        given(voteRepository.findAll()).willReturn(List.of());
        assertThrows(ObjectNotFoundException.class, () -> {

        voteService.getAllVotes();
    });
    }

}

