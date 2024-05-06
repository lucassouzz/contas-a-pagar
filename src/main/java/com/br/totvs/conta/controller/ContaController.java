package com.br.totvs.conta.controller;

import com.br.totvs.conta.Conta;
import com.br.totvs.conta.enumeration.SituacaoEnum;
import com.br.totvs.conta.payload.ContaRequest;
import com.br.totvs.conta.payload.GastosResponse;
import com.br.totvs.conta.service.ContaService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("conta")
public class ContaController {

    public final ContaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Conta create(@Valid @RequestBody ContaRequest request) {
        Conta entity = ContaRequest.toEntity(request);

        return service.save(entity);
    }

    @PutMapping("{id}")
    public Conta update(@PathVariable Long id, @Valid @RequestBody ContaRequest request) {
        Conta entity = ContaRequest.toEntity(request);

        return service.update(id, entity);
    }

    @PutMapping("{id}/alterar/{situacao}")
    public void changeSituacaoById(@PathVariable Long id, @PathVariable SituacaoEnum situacao) {
        service.changeSituacaoById(id, situacao);
    }

    @GetMapping("{id}")
    public Conta getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("filtrar")
    public Page<Conta> getByFilter(@RequestParam(required = false) @DateTimeFormat LocalDate vencimento,
                                   @RequestParam(required = false) String descricao,
                                   Pageable pageable) {
        return service.findByFilter(vencimento, descricao, pageable);
    }

    @GetMapping("valor_gasto")
    public GastosResponse getGastosByPeriodo(@RequestParam @DateTimeFormat LocalDate dataInicial,
                                              @RequestParam @DateTimeFormat LocalDate dataFinal) {
        return service.findGastosByPeriodo(dataInicial, dataFinal);
    }

    @PostMapping("upload")
    public void upload(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        service.upload(multipartFile);
    }
}
