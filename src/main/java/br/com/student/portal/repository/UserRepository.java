package br.com.student.portal.repository;

import br.com.student.portal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    // ✅ Mantém como Optional<UserEntity> para uso geral
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByRegistration(String registration);

    // ✅ Método EXISTS para verificações rápidas
    boolean existsByEmail(String email);

    boolean existsByRegistration(String registration);
}