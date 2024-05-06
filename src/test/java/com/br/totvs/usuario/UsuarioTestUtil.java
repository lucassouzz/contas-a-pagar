package com.br.totvs.usuario;

import com.br.totvs.usuario.payload.LoginRequest;

public class UsuarioTestUtil {

    public static final Long ID = 1L;
    public static final String EMAIL = "test@test.com.br";
    public static final String PASSWORD = "Test@123";
    public static final String PASSWORD_ENCRYPTED = "I90/SM4SztgRIxdxfzm4Rw==";
    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJpYXQiOjE3MTUwMzAzOTUsInN1YiI6Ikpzb25XZWJUb2tlbiIsImV4cCI6MTcxNTAzMDY5NX" +
            "0.eX9mZQ9C2krLKtKLvE4e__BLCYYvuzuqzsEgaW0XmVY";

    public static Usuario createUsuario() {
        return Usuario.builder()
                .id(ID)
                .email(EMAIL)
                .password(PASSWORD_ENCRYPTED)
                .build();
    }

    public static LoginRequest createLoginRequest() {
        return LoginRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }
}
