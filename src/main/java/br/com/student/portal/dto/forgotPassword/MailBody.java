package br.com.student.portal.dto.forgotPassword;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {

}
