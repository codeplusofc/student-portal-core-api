package br.com.student.portal.service;

import br.com.student.portal.entity.AgendaEntity;
import br.com.student.portal.repository.AgendaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static br.com.student.portal.data.FixedData.AGENDA_ID;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class AgendaServiceTest {

    @Mock
    AgendaRepository agendaRepository;

    @InjectMocks
    AgendaService agendaService;

    @Test
    public void mustInsertSession(){
        //MOCAR(SIMULAR) OS DADOS
        //SALVAR EM UMA VARIAVEL O RETORNO DA FUNÇÃO QUE ESTAMOS TESTANDO
        //FAZER OS ASSERTS
        var agendaEntity = mock(AgendaEntity.class);

        given(agendaEntity.getId()).willReturn(AGENDA_ID);
        given(agendaRepository.findById(agendaEntity.getId())).willReturn(Optional.of(agendaEntity));
        given(agendaRepository.save(agendaEntity)).willReturn(agendaEntity);

        var result = agendaService.insertSession(agendaEntity);

        assertEquals(AGENDA_ID, result.getId());

    }
}
