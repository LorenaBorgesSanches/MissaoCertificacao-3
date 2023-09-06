package com.notificacoes.pje.dto;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Getter;

@Getter
@ApiResponses
public class ErrorDTO {

    private String message;

    public ErrorDTO(String message){
        this.message = message;
    }
}
