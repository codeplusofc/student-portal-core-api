package br.com.student.portal.service;

import br.com.student.portal.dto.vote.AgendaResultDTO;
import br.com.student.portal.entity.AgendaEntity;

import br.com.student.portal.repository.AgendaRepository;

import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.AgendaRepository;
import jakarta.validation.constraints.Null;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import java.util.List;

import java.util.Optional;

import static br.com.student.portal.data.FixedData.AGENDA_ID;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.junit.Assert.assertEquals;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AgendaServiceTest {
    @Mock
    private AgendaRepository agendaRepository;
    @Mock
    private AgendaEntity agendaEntity;
    @Mock
    private AgendaEntity agendaResponse;
    @InjectMocks
    private AgendaService agendaService;

    @Before
    public void setup() {
        given(agendaEntity.getId()).willReturn(AGENDA_ID);
        given(agendaEntity.getName()).willReturn("Retirada do presidente");
        given(agendaEntity.getDeadline()).willReturn(LocalDateTime.of(2025, 8, 7, 11, 24, 53));
    }

    @Test
    public void mustInsertSession() {
        given(agendaRepository.findById(agendaEntity.getId())).willReturn(Optional.of(agendaEntity));
        given(agendaRepository.save(agendaEntity)).willReturn(agendaEntity);
        var result = agendaService.insertSession(agendaEntity);
        assertEquals(AGENDA_ID, result.getId());
    }

    @Test
    public void mustCreateAgenda() {
        given(agendaRepository.save(agendaEntity)).willReturn(agendaEntity);
        var result = agendaService.createAgenda(agendaEntity);
        assertEquals("Retirada do presidente", result.getName());
        assertEquals(AGENDA_ID, result.getId());
    }

    @Test
    public void mustFindAllAgendas() {
        var agendas = List.of(agendaEntity, agendaEntity);
        given(agendaRepository.findAll()).willReturn(agendas);
        var result = agendaService.agendaFindAll();
        assertEquals(agendas, result);
    }

    @Test
    public void mustFindAgendaById() {
        var deadline = LocalDateTime.of(2025, 8, 7, 11, 24, 53);
        given(agendaRepository.findById(AGENDA_ID)).willReturn(Optional.of(agendaEntity));
        var result = agendaService.agendaFindById(AGENDA_ID);
        assertEquals(Optional.of(agendaEntity), result);
        assertEquals("Retirada do presidente", agendaEntity.getName());
        assertEquals(deadline, result.get().getDeadline());
    }

    @Test
    public void mustNotFindAgenda() {
        assertThrows(ObjectNotFoundException.class, () -> {
            agendaService.agendaFindAll();
        });
    }
    @Test
    public void mustNotFindFindById(){
        given(agendaRepository.findById(AGENDA_ID)).willReturn(Optional.empty());
        ObjectNotFoundException exception = assertThrows(
                ObjectNotFoundException.class,
                () -> agendaService.agendaFindById(AGENDA_ID)
        );
        assertEquals("Agenda not found", exception.getMessage());



    }
    @Test
    public void mustSetDefaultDeadline(){
        given(agendaEntity.getDeadline()).willReturn(LocalDateTime.of(2025, 8, 7, 11, 24, 53));
        given(agendaResponse.getDeadline()).willReturn(LocalDateTime.of(2025,9,3,12,22,34));
        given(agendaRepository.save(agendaEntity)).willReturn(agendaResponse);
        var result = agendaService.isDeadLineUpdateNeeded(agendaEntity, agendaResponse);


        assertEquals(agendaResponse, result);
    }
}




