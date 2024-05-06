package com.br.totvs.usuario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Usuário com o e-mail %s não encontrado.";

    public UsuarioNotFoundException(String email) {
        super(String.format(MESSAGE, email));
    }
}
