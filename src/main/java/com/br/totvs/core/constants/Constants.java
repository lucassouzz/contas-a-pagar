package com.br.totvs.core.constants;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

    private static Constants instance;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${password.secret}")
    private String secret;

    @Value("${password.algorithm}")
    private String algorithm;

    @PostConstruct
    private void init() {
        instance = this;
    }

    @Getter
    @AllArgsConstructor
    public enum Password {
        SECRET(Constants.instance.secret),
        ALGORITHM(Constants.instance.algorithm);

        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum Jwt {
        SECRET(Constants.instance.jwtSecret);

        private final String value;
    }
}
