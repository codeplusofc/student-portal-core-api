package br.com.student.portal.service;


import br.com.student.portal.dto.forgotPassword.MailBody;

import br.com.student.portal.exception.BadRequestException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(MailBody mailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.to());
        message.setFrom("contatocodeplus@gmail.com");
        message.setSubject(mailBody.subject());
        message.setText(mailBody.text());
        try{
            javaMailSender.send(message);
        }catch (MailException exception) {
            throw new MailSendException("Email not found" + mailBody, exception);
        }

        javaMailSender.send(message);


    }
}
