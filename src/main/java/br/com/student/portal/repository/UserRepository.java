package br.com.student.portal.repository;

import br.com.student.portal.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserDetails findByEmail(String login);
    UserEntity findUserByEmail(String email);
    @Transactional
    @Modifying
    @Query("update UserEntity u set u.password = ?2 where u.email = ?1")
    void updatePassword(String email, String password);


}
