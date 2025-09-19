package br.com.student.portal.dto.forgotPassword.otp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OtpRequest {
    String otp;
    String email;
}
