package com.notificacoes.pje.dto;

import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Component;
import com.notificacoes.pje.model.Endereco;
import com.notificacoes.pje.model.Pessoa;
import com.notificacoes.pje.repository.EnderecoRepository;
import lombok.Getter;
import reactor.core.publisher.Mono;

@Getter
@Component // Anote como um componente Spring
public class PessoaCriacaoDTO {
    private String nome;
    private String documento;
    private String cep;
    private String numeroEndereco;
    private String complemento;
    private String email;
    public WebClient client;
    public EnderecoRepository enderecoRepo;

    public Pessoa converterParaModel(EnderecoRepository enderecoRepo) {
        WebClient client = WebClient.create("https://viacep.com.br/");

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setDocumento(documento);
        pessoa.setNumeroEndereco(numeroEndereco);
        pessoa.setComplemento(complemento);
        pessoa.setEmail(email);

        if (!StringUtils.hasText(cep)) {
            return pessoa;
        }

        Endereco endereco = enderecoRepo.findByCep(cep);
        if (endereco != null) {
            pessoa.setEndereco(endereco);
        } else {
            EnderecoViaCepDTO enderecoViaCep = client.get()
                    .uri("ws/" + cep + "/json/")
                    .retrieve()
                    .bodyToMono(EnderecoViaCepDTO.class)
                    .onErrorResume(e -> {
                        // Tratar erro de chamada à API
                        return Mono.empty();
                    })
                    .block();

            if (enderecoViaCep != null) {
                endereco = new Endereco();
                endereco.setCep(cep);
                endereco.setLogradouro(enderecoViaCep.getLogradouro());
                endereco.setBairro(enderecoViaCep.getBairro());
                endereco.setLocalidade(enderecoViaCep.getLocalidade());
                endereco.setUf(enderecoViaCep.getUf());

                // Salvando o endereço no banco de dados
                endereco = enderecoRepo.save(endereco);
                pessoa.setEndereco(endereco);
            }
        }

        return pessoa;
    }
}
