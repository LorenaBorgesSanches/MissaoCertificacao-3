package com.notificacoes.pje.dto;

import com.notificacoes.pje.enums.StatusNotificacao;
import com.notificacoes.pje.enums.TipoDocumento;
import com.notificacoes.pje.model.Notificacao;
import com.notificacoes.pje.model.Pessoa;

import lombok.Getter;

@Getter
public class NotificacaoCriacaoDTO {
    private String documento;
    private String numProcesso;
    private TipoDocumento tipoDocumento;
    private String descricao;

    public Notificacao converterParaModel(Pessoa pessoa) {
        Notificacao notificacao = new Notificacao();
        notificacao.setPessoa(pessoa);
        notificacao.setNumProcesso(numProcesso);
        notificacao.setTipoDocumento(tipoDocumento);
        notificacao.setDescricao(descricao);

        notificacao.setDataCadastro(java.time.LocalDateTime.now());
        notificacao.setStatus(StatusNotificacao.Rascunho);
        return notificacao;
    }
}
