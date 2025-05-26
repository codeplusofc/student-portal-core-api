package br.com.student.portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


import java.util.UUID;
@Data
@Entity
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private boolean vote;
    private UUID userId;
    private UUID agendaId;

}
