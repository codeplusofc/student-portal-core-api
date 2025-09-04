package br.com.student.portal.dto.vote;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {
    private boolean vote;
    private UUID userId;
    private UUID agendaId;


}
