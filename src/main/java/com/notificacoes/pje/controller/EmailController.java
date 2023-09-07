package com.notificacoes.pje.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notificacoes.pje.dto.EmailDto;
import com.notificacoes.pje.model.EmailModel;
import com.notificacoes.pje.services.EmailService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<EmailModel> enviarEmail(@RequestBody @Valid EmailDto emailDto){
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailService.enviarEmail(emailModel);
        return new ResponseEntity<EmailModel>(emailModel, HttpStatus.CREATED);
    }
}
