package com.br.totvs.conta.controller;

import com.br.totvs.conta.Conta;
import com.br.totvs.conta.enumeration.SituacaoEnum;
import com.br.totvs.conta.payload.ContaRequest;
import com.br.totvs.conta.payload.GastosResponse;
import com.br.totvs.conta.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
@RestController
@RequestMapping("conta")
public class ContaController {

    public final ContaService service;

    @Operation(summary = "Criar uma nova Conta.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Conta create(@Valid @RequestBody ContaRequest request) {
        Conta entity = ContaRequest.toEntity(request);

        return service.save(entity);
    }

    @Operation(summary = "Atualizar uma Conta já cadastrada por id.")
    @PutMapping("{id}")
    public Conta update(@PathVariable Long id, @Valid @RequestBody ContaRequest request) {
        Conta entity = ContaRequest.toEntity(request);

        return service.update(id, entity);
    }

    @Operation(summary = "Alterar a Situação de uma Conta")
    @PutMapping("{id}/alterar/{situacao}")
    public void changeSituacaoById(@PathVariable Long id, @PathVariable SituacaoEnum situacao) {
        service.changeSituacaoById(id, situacao);
    }

    @Operation(summary = "Buscar Conta por id.")
    @GetMapping("{id}")
    public Conta getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Filtar contas por Descrição, Vencimento ou ambos.")
    @GetMapping("filtrar")
    public Page<Conta> getByFilter(@RequestParam(required = false) @DateTimeFormat LocalDate vencimento,
                                   @RequestParam(required = false) String descricao,
                                   Pageable pageable) {
        return service.findByFilter(vencimento, descricao, pageable);
    }

    @Operation(summary = "Buscar valor gasto por período.")
    @GetMapping("valor_gasto")
    public GastosResponse getGastosByPeriodo(@RequestParam @DateTimeFormat LocalDate dataInicial,
                                             @RequestParam @DateTimeFormat LocalDate dataFinal) {
        return service.findGastosByPeriodo(dataInicial, dataFinal);
    }

    @Operation(summary = "Importar novas Contas.")
    @PostMapping("upload")
    public void upload(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        service.upload(multipartFile);
    }
}
