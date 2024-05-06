package com.br.totvs.usuario.service;

import com.br.totvs.usuario.Usuario;
import com.br.totvs.usuario.exceptions.UsuarioNotFoundException;
import com.br.totvs.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.br.totvs.core.util.JwtUtil.getBearerToken;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public String login(String email, String password) throws Exception {
        Usuario usuario = findByEmail(email);

        return getBearerToken(password, usuario.getPassword());
    }

    public Usuario findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UsuarioNotFoundException(email));
    }
}
