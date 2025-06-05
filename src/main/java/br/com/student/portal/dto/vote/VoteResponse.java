package br.com.student.portal.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class VoteResponse {
    private UUID id;
    private boolean vote;
    private UUID userId;
    private UUID agendaId;

}
