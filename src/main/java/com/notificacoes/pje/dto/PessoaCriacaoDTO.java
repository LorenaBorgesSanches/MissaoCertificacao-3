package com.notificacoes.pje.dto;

import org.springframework.web.reactive.function.client.WebClient;

import com.notificacoes.pje.model.Endereco;
import com.notificacoes.pje.model.Pessoa;
import com.notificacoes.pje.repository.EnderecoRepository;

import lombok.Getter;
import reactor.core.publisher.Mono;

@Getter
public class PessoaCriacaoDTO {
    private String nome;
    private String documento;
    private String cep;
    private String numeroEndereco;
    private String complemento;
    private String email;

    public Pessoa converterParaModel(EnderecoRepository enderecoRepo) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setDocumento(documento);
        pessoa.setNumeroEndereco(numeroEndereco);
        pessoa.setComplemento(complemento);
        pessoa.setEmail(email);

        if (cep == null || cep == "")
            return pessoa;

        Endereco endereco = enderecoRepo.findByCep(cep);
        if (endereco != null)
            pessoa.setEndereco(endereco);
        else {
            WebClient client = WebClient.create("https://viacep.com.br/");
            EnderecoViaCepDTO enderecoViaCep = client.get()
                    .uri("ws/" + cep + "/json/")
                    .retrieve()
                    .bodyToMono(EnderecoViaCepDTO.class).block();
            
            endereco = new Endereco();
            endereco.setCep(cep);
            endereco.setLogradouro(enderecoViaCep.getLogradouro());
            endereco.setBairro(enderecoViaCep.getBairro());
            endereco.setLocalidade(enderecoViaCep.getLocalidade());
            endereco.setUf(enderecoViaCep.getUf());
            pessoa.setEndereco(enderecoRepo.save(endereco));
        }

        return pessoa;
    }
}
