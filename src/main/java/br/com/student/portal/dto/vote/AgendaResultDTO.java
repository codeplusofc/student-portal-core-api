package br.com.student.portal.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgendaResultDTO {
    private UUID idAgenda;
    private long yes;
    private long no;
    private String result;

}
