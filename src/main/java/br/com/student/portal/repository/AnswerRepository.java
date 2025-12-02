package br.com.student.portal.repository;

import br.com.student.portal.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, UUID> {
    @Query("SELECT a FROM AnswerEntity a WHERE a.question.id = :questionId ORDER BY a.createdAt ASC")
    List<AnswerEntity> findByQuestionId(@Param("questionId") UUID questionId);

    @Query("SELECT a FROM AnswerEntity a WHERE a.author.id = :authorId ORDER BY a.createdAt DESC")
    List<AnswerEntity> findByAuthorId(@Param("authorId") UUID authorId);

    @Query("SELECT COUNT(a) FROM AnswerEntity a WHERE a.question.id = :questionId")
    long countByQuestionId(@Param("questionId") UUID questionId);
}