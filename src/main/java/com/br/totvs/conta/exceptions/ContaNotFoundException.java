package com.br.totvs.conta.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContaNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Conta com identificador %s não encontrada.";

    public ContaNotFoundException(Long id) {
        super(String.format(MESSAGE, id));
    }
}
