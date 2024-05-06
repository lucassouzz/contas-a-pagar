package com.br.totvs.conta.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CsvInvalidFormatException extends RuntimeException {

    private static final String MESSAGE = "A extensão do arquivo está incorreta, a extensão esperada é .csv.";

    public CsvInvalidFormatException() {
        super(MESSAGE);
    }
}
