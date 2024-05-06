package com.br.totvs.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedException extends RuntimeException {

    private static final String MESSAGE = "Usuário não autorizado.";

    public UnauthorizedException() {
        super(MESSAGE);
    }
}
