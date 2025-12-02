package br.com.student.portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private boolean vote;
    private UUID userId;
    private UUID agendaId;


    public VoteEntity(boolean vote, UUID userId, UUID agendaId) {
        this.vote = vote;
        this.userId = userId;
        this.agendaId = agendaId;
    }
}
