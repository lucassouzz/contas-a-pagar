package com.br.totvs.conta.service;

import com.br.totvs.conta.Conta;
import com.br.totvs.conta.dto.ContaCsvDto;
import com.br.totvs.conta.enumeration.SituacaoEnum;
import com.br.totvs.conta.exceptions.ContaNotFoundException;
import com.br.totvs.conta.exceptions.ReadCsvErrorException;
import com.br.totvs.conta.payload.GastosResponse;
import com.br.totvs.conta.repository.ContaRepository;
import com.br.totvs.core.util.CsvUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.br.totvs.conta.repository.ContaSpecification.buildFilter;
import static com.br.totvs.core.util.FileUtil.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContaService {

    private final ContaRepository repository;

    public Conta findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ContaNotFoundException(id));
    }

    public Conta save(Conta conta) {
        return repository.save(conta);
    }

    public List<Conta> saveAll(List<Conta> contas) {
        return repository.saveAll(contas);
    }

    public Conta update(Long id, Conta conta) {
        Conta old = findById(id);
        conta.setId(old.getId());

        return repository.save(conta);
    }

    public Page<Conta> findByFilter(LocalDate vencimento, String descricao, Pageable pageable) {
        Specification<Conta> filter = buildFilter(vencimento, descricao);

        return repository.findAll(filter, pageable);
    }

    public void upload(MultipartFile multipartFile) throws IOException {
        File file = toFile(multipartFile);

        validFormat(file, CSV_FORMAT);

        List<Conta> contas = readFile(file);

        saveAll(contas);
    }

    public GastosResponse findGastosByPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
        return repository.findGastosByPeriodo(dataInicial, dataFinal);
    }

    public Conta changeSituacaoById(Long id, SituacaoEnum situacao) {
        Conta conta = findById(id);
        conta.setSituacao(situacao);

        return save(conta);
    }

    private List<Conta> readFile(File file) {
        try {
            List<ContaCsvDto> items = new CsvUtil<ContaCsvDto>().read(file, ContaCsvDto.class);

            return items.stream()
                    .map(ContaCsvDto::toEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ReadCsvErrorException();
        }
    }
}
