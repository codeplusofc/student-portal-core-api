package br.com.student.portal.service;

import br.com.student.portal.dto.forgotPassword.MailBody;
import br.com.student.portal.dto.forgotPassword.otp.ChangePasswordRequest;
import br.com.student.portal.dto.forgotPassword.otp.OtpRequest;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.entity.forgotPassword.ForgotPasswordEntity;
import br.com.student.portal.exception.BadRequestException;
import br.com.student.portal.repository.ForgotPasswordRepository;
import br.com.student.portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;


@Service
public class ForgotPasswordService {
    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public ResponseEntity<String> verifyEmail(String email){
        UserEntity user = userRepository.findUserByEmail(email);
        if(user == null){
            return ResponseEntity.badRequest().body("Please provide a valid email");
        }


        String otp = otpGenerator();

        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your forget password request" + otp)
                .subject("OTP for Forgot Password Request")
                .build();

        ForgotPasswordEntity forgotPassword = ForgotPasswordEntity.builder()
                .otp(otp)
                .expirationTime(LocalDateTime.now().plusMinutes(8))
                .user(user)
                .build();
       emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(forgotPassword);

        return ResponseEntity.ok("Email sent for verification");

    }


    private String otpGenerator() {
        SecureRandom secureRandom = new SecureRandom();
        int otp = 100_000 + secureRandom.nextInt(900_000);
        return String.format("%06d", otp);
    }



    public ResponseEntity<String> verifyOtp(OtpRequest otpRequest){
        UserEntity user = userRepository.findUserByEmail(otpRequest.getEmail());
        if(user == null){
            return ResponseEntity.badRequest().body("Please provide a valid email");
        }
        ForgotPasswordEntity forgotPasswordEntity = forgotPasswordRepository
                .findByOtpAndUser(otpRequest.getOtp(), user)
                .orElse(null);

        if (forgotPasswordEntity == null) {
            return ResponseEntity.badRequest().body("Invalid OTP for this email");
        }

        if(forgotPasswordEntity.getExpirationTime().isBefore(LocalDateTime.now())) {
            forgotPasswordRepository.deleteById(forgotPasswordEntity.getForgotPasswordId());
            return ResponseEntity.status(422).body("OTP has expired");
        }
        forgotPasswordRepository.deleteById(forgotPasswordEntity.getForgotPasswordId());
        return ResponseEntity.ok("OTP verified successfully");
    }

    public ResponseEntity<String> changePassword(ChangePasswordRequest changePasswordRequest){
        if(!Objects.equals(changePasswordRequest.password(), changePasswordRequest.repeatPassword())){
            throw new BadRequestException("The password doesnt match");
        }

        String encodedPassword = passwordEncoder.encode(changePasswordRequest.password());
        userRepository.updatePassword(changePasswordRequest.email(), encodedPassword);
        return ResponseEntity.ok("Password changed");
    }



}
