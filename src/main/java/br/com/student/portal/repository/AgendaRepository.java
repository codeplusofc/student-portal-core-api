package br.com.student.portal.repository;

import br.com.student.portal.entity.AgendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaEntity, UUID> {
}
