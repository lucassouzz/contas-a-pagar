package com.br.totvs.core.util;

import com.br.totvs.core.constants.Constants;
import com.br.totvs.core.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import static com.br.totvs.core.constants.Constants.Jwt.SECRET;
import static com.br.totvs.core.util.PasswordUtil.decrypt;

public class JwtUtil {

//    private static final long EXPIRATION_TIME = 300000;
    private static final long EXPIRATION_TIME = 30000000;

    public static String getBearerToken(String requestPassword, String originalPassword) throws Exception {
        validatePassword(requestPassword, originalPassword);

        return buildToken();
    }

    private static void validatePassword(String requestPassword, String originalPassword) throws Exception {
        String passwordDecrypt = decrypt(originalPassword);

        if (!requestPassword.equals(passwordDecrypt)) {
            throw new UnauthorizedException();
        }
    }

    private static String buildToken() {
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject("JsonWebToken")
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET.getValue())
                .compact();
    }
}
