package com.notificacoes.pje.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notificacoes.pje.dto.ErrorDTO;
import com.notificacoes.pje.enums.StatusNotificacao;
import com.notificacoes.pje.enums.TipoLote;
import com.notificacoes.pje.enums.TipoNotificacao;
import com.notificacoes.pje.model.LoteEnvio;
import com.notificacoes.pje.model.Notificacao;
import com.notificacoes.pje.repository.LoteRepository;
import com.notificacoes.pje.repository.NotificaoRepository;

@RestController
@RequestMapping(path = "/integracao")
public class IntegracaoController {

    @Autowired
    private NotificaoRepository notificacaoRepo;
    @Autowired
    private LoteRepository loteRepo;

    @GetMapping(path = "/ecartas")
    public List<Notificacao> listarCartas() {
        var notificacoes = notificacaoRepo.FindByStatus(StatusNotificacao.Submetida);
        if (notificacoes.size() < 10) {
            notificacoes.clear();
            return notificacoes;
        }

        for (Notificacao notificacao : notificacoes) {
            notificacao.setDataEnvio(java.time.LocalDateTime.now());
            notificacao.setStatus(StatusNotificacao.Enviada);
            notificacao.setTipoNotificacao(TipoNotificacao.ECartas);
        }
        notificacaoRepo.saveAll(notificacoes);

        LoteEnvio lote = new LoteEnvio();
        lote.setTipoLote(TipoLote.ECartas);
        lote.setNotificacao(notificacoes);
        lote.setNumLote(java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        loteRepo.save(lote);

        return notificacoes;
    }

    @PutMapping(path = "/ecartas/{id}")
    public ResponseEntity<?> atualizarCarta(@PathVariable Integer id) {
        var notificacaoOpt = notificacaoRepo.findById(id);
        if (!notificacaoOpt.isPresent()) {
            return new ResponseEntity<ErrorDTO>(new ErrorDTO("Notificação não encontrada"), HttpStatus.NOT_FOUND);
        }
        var notificacao = notificacaoOpt.get();

        if (notificacao.getTipoNotificacao() != TipoNotificacao.ECartas) {
            return new ResponseEntity<ErrorDTO>(new ErrorDTO("Notificação não pertence ao ECartas"),
                    HttpStatus.BAD_REQUEST);
        }

        if (notificacao.getStatus() == StatusNotificacao.Recebida) {
            return new ResponseEntity<ErrorDTO>(new ErrorDTO("Notificação já recebida"), HttpStatus.BAD_REQUEST);
        }

        if (notificacao.getStatus() != StatusNotificacao.Enviada) {
            return new ResponseEntity<ErrorDTO>(new ErrorDTO("Notificação em status não permitido"),
                    HttpStatus.BAD_REQUEST);
        }

        notificacao.setStatus(StatusNotificacao.Recebida);
        notificacao.setDataRecebimento(java.time.LocalDateTime.now());
        notificacaoRepo.save(notificacao);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/dje")
    public List<Notificacao> listarDje() {
        var notificacoes = notificacaoRepo.FindByStatusEnderecoNullEmailNull(StatusNotificacao.Submetida);
        if (notificacoes.size() == 0) {
            return notificacoes;
        }

        for (Notificacao notificacao : notificacoes) {
            notificacao.setDataEnvio(java.time.LocalDateTime.now());
            notificacao.setStatus(StatusNotificacao.Enviada);
            notificacao.setTipoNotificacao(TipoNotificacao.DJe);
        }
        notificacaoRepo.saveAll(notificacoes);

        LoteEnvio lote = new LoteEnvio();
        lote.setTipoLote(TipoLote.DJe);
        lote.setNotificacao(notificacoes);
        lote.setNumLote(java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        loteRepo.save(lote);

        return notificacoes;
    }

    @PutMapping(path = "/dje/{id}")
    public ResponseEntity<?> atualizarDje(@PathVariable Integer id) {
        var notificacaoOpt = notificacaoRepo.findById(id);
        if (!notificacaoOpt.isPresent()) {
            return new ResponseEntity<ErrorDTO>(new ErrorDTO("Notificação não encontrada"), HttpStatus.NOT_FOUND);
        }
        var notificacao = notificacaoOpt.get();

        if (notificacao.getTipoNotificacao() != TipoNotificacao.DJe) {
            return new ResponseEntity<ErrorDTO>(new ErrorDTO("Notificação não pertence ao DJe"),
                    HttpStatus.BAD_REQUEST);
        }

        if (notificacao.getStatus() == StatusNotificacao.Recebida) {
            return new ResponseEntity<ErrorDTO>(new ErrorDTO("Notificação já recebida"), HttpStatus.BAD_REQUEST);
        }

        if (notificacao.getStatus() != StatusNotificacao.Enviada) {
            return new ResponseEntity<ErrorDTO>(new ErrorDTO("Notificação em status não permitido"),
                    HttpStatus.BAD_REQUEST);
        }

        notificacao.setStatus(StatusNotificacao.Recebida);
        notificacao.setDataRecebimento(java.time.LocalDateTime.now());
        notificacaoRepo.save(notificacao);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
