package com.br.totvs.conta.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ReadCsvErrorException extends RuntimeException {

    private static final String MESSAGE = "Ocorreu algo inesperado ao realizar a leitura do arquivo CSV.";

    public ReadCsvErrorException() {
        super(MESSAGE);
    }
}
