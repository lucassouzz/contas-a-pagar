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
import java.util.List;

import static com.br.totvs.core.constants.Constants.Jwt.SECRET;

@Component
public class RequestFilter extends OncePerRequestFilter {

    private static final List<String> PATHS = List.of("/usuario/login", "/v3/api-docs");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return isAllowPath(path);
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

    protected boolean isAllowPath(String path) {
        return PATHS.contains(path) || path.contains("swagger");
    }
}
