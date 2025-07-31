package br.com.student.portal.data;

import br.com.student.portal.entity.AgendaEntity;

import java.time.LocalDateTime;

import static br.com.student.portal.data.FixedData.AGENDA_ID;

public class AgendaEntityData {
    public static AgendaEntity generateAgendaEntity(){
        var agendaEntity = new AgendaEntity();
        agendaEntity.setDeadline(LocalDateTime.now().plusDays(3));
        agendaEntity.setId(AGENDA_ID);
        agendaEntity.setName("Retirada do presidente");
        return agendaEntity;
    }
}
