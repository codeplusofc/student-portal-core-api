package br.com.student.portal.controller;

import br.com.student.portal.dto.forgotPassword.otp.ChangePasswordRequest;
import br.com.student.portal.dto.forgotPassword.otp.OtpRequest;
import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    @Autowired
    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    // Endpoint para enviar OTP por e-mail
    @PostMapping("/verify-mail")
    public ResponseEntity<String> sendEmail(@RequestBody UserRequest userRequest) {
        String email = userRequest.getEmail();
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body("Email must be provided");
        }
        return forgotPasswordService.sendEmail(email);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpRequest otpRequest){
        return forgotPasswordService.verifyOtp(otpRequest);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        return forgotPasswordService.changePassword(changePasswordRequest);
    }
}