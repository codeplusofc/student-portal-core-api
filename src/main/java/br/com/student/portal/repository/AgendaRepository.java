package br.com.student.portal.repository;

import br.com.student.portal.entity.AgendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AgendaRepository extends JpaRepository<AgendaEntity, UUID> {
}
