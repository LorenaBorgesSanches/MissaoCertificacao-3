package com.notificacoes.pje.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.notificacoes.pje.enums.StatusEMail;
import com.notificacoes.pje.model.EmailModel;
import com.notificacoes.pje.repository.EmailRepository;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public EmailModel enviarEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getTitulo());
            message.setText(emailModel.getTexto());

            emailSender.send(message);
            emailModel.setStatusEmail(StatusEMail.ENVIADO);
        } catch (Exception e) {
            emailModel.setStatusEmail(StatusEMail.ERROR);
        } finally {
            return emailRepository.save(emailModel);
        }
    }

}
