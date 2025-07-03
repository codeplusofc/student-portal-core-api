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
    private int yes;
    private int no;
    private String result;

}
