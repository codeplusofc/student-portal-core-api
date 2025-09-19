package br.com.student.portal.entity.forgotPassword;

import br.com.student.portal.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ForgotPasswordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int forgotPasswordId;
    @Column(nullable = false)
    private String otp;
    @Column(nullable = false)
    public LocalDateTime expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

}
