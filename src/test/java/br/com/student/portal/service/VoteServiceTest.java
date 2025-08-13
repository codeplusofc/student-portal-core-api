package br.com.student.portal.service;


import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.dto.vote.VoteRequest;
import br.com.student.portal.dto.vote.VoteResponse;
import br.com.student.portal.entity.AgendaEntity;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.entity.VoteEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.mapper.VoteMapper;
import br.com.student.portal.repository.AgendaRepository;
import br.com.student.portal.repository.UserRepository;
import br.com.student.portal.repository.VoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static br.com.student.portal.data.FixedData.*;
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
    @Mock
    private UserRequest userRequest;
    @Mock
    private UserEntity userEntity;
    @Mock
    private UserResponse userResponse;
    @Mock
    private VoteRequest voteRequest;
    @Mock
    private VoteEntity voteEntity;
    @Mock
    private VoteResponse voteResponse;
    @Mock
    private AgendaEntity agendaEntity;

    @Before
    public void setup() {
        voteRequest = new VoteRequest(true, AGENDA_ID, USER_ID);
        voteEntity = new VoteEntity(VOTE_ID , AGENDA_ID, USER_ID, true);
        voteResponse = new VoteResponse(VOTE_ID, true, USER_ID, AGENDA_ID);
        agendaEntity = new AgendaEntity(AGENDA_ID
                , LocalDateTime.of(2999, 8, 15, 11, 24, 53)
                , "Retirada do presidente");
    }

    @Test
    public void mustCreateVote(){
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
        var votes = List.of(voteEntity, voteEntity);
        var expectedResponses = List.of(voteResponse, voteResponse);

        given(voteRepository.findAll()).willReturn(votes);
        given(voteMapper.voteEntityIntoVoteResponse(voteEntity)).willReturn(voteResponse);
        given(voteMapper.voteEntityIntoVoteResponse(voteEntity)).willReturn(voteResponse);

        var result = voteService.getAllVotes();

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

