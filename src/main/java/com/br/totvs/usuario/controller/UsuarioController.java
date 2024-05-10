package com.br.totvs.usuario.controller;

import com.br.totvs.usuario.payload.LoginRequest;
import com.br.totvs.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Realizar login")
    @PostMapping("login")
    public String login(@RequestBody LoginRequest request) throws Exception {
        return usuarioService.login(request.getEmail(), request.getPassword());
    }
}
