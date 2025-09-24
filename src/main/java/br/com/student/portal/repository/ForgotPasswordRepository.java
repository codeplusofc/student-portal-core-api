package br.com.student.portal.repository;

import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.entity.forgotPassword.ForgotPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordEntity, Integer> {
    Optional<ForgotPasswordEntity> findByOtpAndUser(String otp, UserEntity userEntity);
}
