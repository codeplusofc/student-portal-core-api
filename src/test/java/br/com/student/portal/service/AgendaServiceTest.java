package br.com.student.portal.service;

import br.com.student.portal.entity.AgendaEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.AgendaRepository;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AgendaServiceTest {
    @Mock
    private AgendaRepository agendaRepository;
    @Mock
    private AgendaEntity agendaEntity;
    @InjectMocks
    private AgendaService agendaService;

    @Before
    public void setup() {
        agendaEntity = new AgendaEntity(AGENDA_ID
                ,LocalDateTime.of(2025, 8, 7, 11, 24, 53)
                ,"Retirada do presidente");
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
}
