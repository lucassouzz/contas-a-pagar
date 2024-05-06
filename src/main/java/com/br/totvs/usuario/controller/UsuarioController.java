package com.br.totvs.usuario.controller;

import com.br.totvs.usuario.payload.LoginRequest;
import com.br.totvs.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("login")
    public String login(@RequestBody LoginRequest request) throws Exception {
        return usuarioService.login(request.getEmail(), request.getPassword());
    }
}
