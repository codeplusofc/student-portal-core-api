package br.com.student.portal.validation;

import br.com.student.portal.dto.forgotPassword.otp.OtpRequest;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.entity.forgotPassword.ForgotPasswordEntity;
import br.com.student.portal.exception.BadRequestException;
import br.com.student.portal.repository.ForgotPasswordRepository;
import br.com.student.portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ForgotPasswordValidator {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    public UserEntity findByEmail(String email) {
        if (userRepository.findByEmail(email).getEmail().isEmpty()) {
            throw new BadRequestException("No email found");
        }
        return userRepository.findByEmail(email);
    }




    public void validateEmailFields(OtpRequest otpRequest) {
        UserEntity user = findByEmail(otpRequest.getEmail());
        if (user == null) {
            throw new BadRequestException("Please provide a valid email");
        }

    }
    public void validateForgotPasswordFields(ForgotPasswordEntity forgotPasswordEntity){
        if (forgotPasswordEntity == null) {
            throw new BadRequestException("Invalid OTP for this email");
        }

        if(forgotPasswordEntity.getExpirationTime().isBefore(LocalDateTime.now())) {
            forgotPasswordRepository.deleteById(forgotPasswordEntity.getForgotPasswordId());
            throw new BadRequestException("OTP has expired");
        }
    }
}
