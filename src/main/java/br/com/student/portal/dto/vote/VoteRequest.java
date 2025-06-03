package br.com.student.portal.dto.vote;

import lombok.Data;

import java.util.UUID;
@Data
public class VoteRequest {
    private boolean vote;
    private UUID userId;
    private UUID agendaId;
}
