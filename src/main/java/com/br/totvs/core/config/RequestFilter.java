package com.br.totvs.core.config;

import com.br.totvs.core.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.br.totvs.core.constants.Constants.Jwt.SECRET;

@Component
public class RequestFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return path.contains("/usuario/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();

            Jwts.parserBuilder()
                    .setSigningKey(SECRET.getValue())
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            throw new UnauthorizedException();
        }

        filterChain.doFilter(request, response);
    }
}
