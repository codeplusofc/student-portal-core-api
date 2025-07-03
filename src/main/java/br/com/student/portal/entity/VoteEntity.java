package br.com.student.portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private boolean vote;
    private UUID userId;
    private UUID agendaId;

    public VoteEntity(UUID agendaId, UUID userId, boolean vote) {
        this.agendaId = agendaId;
        this.userId = userId;
        this.vote = vote;

    }
}
