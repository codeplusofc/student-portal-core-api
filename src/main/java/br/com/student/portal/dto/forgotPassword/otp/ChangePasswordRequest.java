package br.com.student.portal.dto.forgotPassword.otp;

public record ChangePasswordRequest(String email, String password, String repeatPassword) {
}
