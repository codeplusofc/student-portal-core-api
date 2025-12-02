package br.com.student.portal.repository;

import br.com.student.portal.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {

    @Query("SELECT q FROM QuestionEntity q WHERE " +
            "LOWER(q.title) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(q.content) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<QuestionEntity> searchByTerm(@Param("term") String term);

    List<QuestionEntity> findAllByOrderByCreatedAtDesc();

    // ❌ REMOVA ESTA LINHA (já existe no JpaRepository):
    // Optional<QuestionEntity> findById(UUID uuid);
}