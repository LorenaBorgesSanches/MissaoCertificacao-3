package com.notificacoes.pje.dto;

import com.notificacoes.pje.model.Endereco;
import com.notificacoes.pje.model.Pessoa;
import com.notificacoes.pje.repository.EnderecoRepository;

import lombok.Getter;

@Getter
public class PessoaCriacaoDTO {
    private String nome;
    private String documento;
    private String cep;
    private String numeroEndereco;
    private String complemento;
    private String email;

    public Pessoa converterParaPessoa(EnderecoRepository enderecoRepo) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setDocumento(documento);
        pessoa.setNumeroEndereco(numeroEndereco);
        pessoa.setComplemento(complemento);
        pessoa.setEmail(email);

        Endereco endereco = enderecoRepo.findByCep(cep);
        if(endereco != null)
            pessoa.setEndereco(endereco);
        else{
            endereco = new Endereco();
            endereco.setCep(cep);
            endereco.setLogradouro("teste");
            pessoa.setEndereco(enderecoRepo.save(endereco));
        }
        
        return pessoa;
    }
}
