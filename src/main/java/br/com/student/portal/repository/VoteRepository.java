package br.com.student.portal.repository;

import br.com.student.portal.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, UUID> {
    Optional<VoteEntity> findByUserIdAndAgendaId(UUID idUser, UUID agendaId);
    List<VoteEntity> findByAgendaId(UUID agendaId);

}
