package com.notificacoes.pje.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notificacoes.pje.dto.ErrorDTO;
import com.notificacoes.pje.dto.NotificacaoCriacaoDTO;
import com.notificacoes.pje.enums.StatusNotificacao;
import com.notificacoes.pje.model.EmailModel;
import com.notificacoes.pje.model.Notificacao;
import com.notificacoes.pje.model.Pessoa;
import com.notificacoes.pje.repository.NotificaoRepository;
import com.notificacoes.pje.repository.PessoaRepository;
import com.notificacoes.pje.services.EmailService;

@RestController
@RequestMapping(path = "/notificacoes")
public class NotificacaoController {

    @Autowired
    private PessoaRepository pessoaRepo;
    @Autowired
    private NotificaoRepository notificacaoRepo;
    @Autowired
    private EmailService emailService;

    @GetMapping
    public List<Notificacao> listar() {
        return notificacaoRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody NotificacaoCriacaoDTO dto) {
        Pessoa pessoa = pessoaRepo.findByDocumento(dto.getDocumento());
        if (pessoa == null) {
            return new ResponseEntity<ErrorDTO>(new ErrorDTO("Pessoa não encontrada"), HttpStatus.NOT_FOUND);
        }

        Notificacao notificacao = notificacaoRepo.save(dto.converterParaModel(pessoa));

        return new ResponseEntity<>(notificacao, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> submeter(@PathVariable Integer id) {
        var notificacaoOpt = notificacaoRepo.findById(id);
        if (!notificacaoOpt.isPresent()) {
            return new ResponseEntity<ErrorDTO>(new ErrorDTO("Notificação não encontrada"), HttpStatus.NOT_FOUND);
        }
        var notificacao = notificacaoOpt.get();

        if (notificacao.getStatus() != StatusNotificacao.Rascunho) {
            return new ResponseEntity<ErrorDTO>(new ErrorDTO("Notificação não está em rascunho"),
                    HttpStatus.BAD_REQUEST);
        }

        notificacao.setStatus(StatusNotificacao.Submetida);
        notificacao.setDataSubmissao(java.time.LocalDateTime.now());
        notificacaoRepo.save(notificacao);

        if (StringUtils.hasText(notificacao.getPessoa().getEmail())) {
            String emailBody = "Prezado,\nAbaixo notificação referente ao processo " + notificacao.getNumProcesso();
            emailBody += ".\nFoi expedido um documento do tipo \"" + notificacao.getTipoDocumento() + "\" para o seu e-mail, ";
            emailBody += "com o seguinte conteúdo: \n\n" + notificacao.getDescricao();
            emailBody += "\n\n Att,\nSistema de Notificação do PJE";

            EmailModel emailModel = new EmailModel();
            emailModel.setEmailFrom("time5pje@gmail.com");
            emailModel.setEmailTo(notificacao.getPessoa().getEmail());
            emailModel.setTitulo("Notifição PJE: " + notificacao.getNumProcesso());
            emailModel.setTexto(emailBody);
            emailService.enviarEmail(emailModel);
        }

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
