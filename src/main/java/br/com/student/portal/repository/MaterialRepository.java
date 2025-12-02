package br.com.student.portal.repository;

import br.com.student.portal.entity.MaterialEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity, UUID> {
    List<MaterialEntity> findByCategory(MaterialEntity.MaterialCategory category);

    @Query("SELECT m FROM MaterialEntity m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :term, '%')) " +
            "OR LOWER(m.description) LIKE LOWER(CONCAT('%', :term, '%')) " +
            "ORDER BY m.uploadDate DESC")
    List<MaterialEntity> searchByName(@Param("term") String term);

    @Query("SELECT m FROM MaterialEntity m ORDER BY m.uploadDate DESC")
    List<MaterialEntity> findAllOrderByNewest();

    @Query("SELECT m FROM MaterialEntity m WHERE m.uploadedBy.id = :userId ORDER BY m.uploadDate DESC")
    List<MaterialEntity> findByUploadedById(@Param("userId") UUID userId);

    Page<MaterialEntity> findAllByOrderByDownloadsDesc(Pageable pageable);
}