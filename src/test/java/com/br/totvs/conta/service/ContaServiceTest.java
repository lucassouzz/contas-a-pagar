package com.br.totvs.conta.service;

import com.br.totvs.conta.Conta;
import com.br.totvs.conta.enumeration.SituacaoEnum;
import com.br.totvs.conta.exceptions.ContaNotFoundException;
import com.br.totvs.conta.exceptions.CsvInvalidFormatException;
import com.br.totvs.conta.exceptions.ReadCsvErrorException;
import com.br.totvs.conta.payload.GastosResponse;
import com.br.totvs.conta.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.br.totvs.conta.ContaTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ContaServiceTest {

    private final Conta entity = createConta();

    @InjectMocks
    private ContaService service;

    @Mock
    private ContaRepository repository;

    @Mock
    private Pageable pageable;

    @Test
    void shouldFindById() {
        when(repository.findById(ID)).thenReturn(Optional.of(entity));

        Conta conta = service.findById(ID);

        verify(repository).findById(anyLong());

        assertEquals(conta.getId(), ID);
        assertEquals(conta.getVencimento(), VENCIMENTO);
        assertEquals(conta.getPagamento(), PAGAMENTO);
        assertEquals(conta.getValor(), VALOR);
        assertEquals(conta.getDescricao(), DESCRICAO);
        assertEquals(conta.getSituacao(), SITUACAO);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenFindById() {
        when(repository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ContaNotFoundException.class, () -> service.findById(ID));

        verify(repository).findById(anyLong());
    }

    @Test
    void shouldSave() {
        when(repository.save(any(Conta.class))).thenReturn(entity);

        Conta conta = service.save(entity);

        verify(repository).save(any(Conta.class));

        assertEquals(conta.getId(), ID);
        assertEquals(conta.getVencimento(), VENCIMENTO);
        assertEquals(conta.getPagamento(), PAGAMENTO);
        assertEquals(conta.getValor(), VALOR);
        assertEquals(conta.getDescricao(), DESCRICAO);
        assertEquals(conta.getSituacao(), SITUACAO);
    }

    @Test
    void shouldSaveAll() {
        List<Conta> entityList = List.of(entity);
        when(repository.saveAll(anyList())).thenReturn(entityList);

        List<Conta> contas = service.saveAll(entityList);

        verify(repository).saveAll(anyList());

        assertEquals(contas.get(0).getId(), ID);
        assertEquals(contas.get(0).getVencimento(), VENCIMENTO);
        assertEquals(contas.get(0).getPagamento(), PAGAMENTO);
        assertEquals(contas.get(0).getValor(), VALOR);
        assertEquals(contas.get(0).getDescricao(), DESCRICAO);
        assertEquals(contas.get(0).getSituacao(), SITUACAO);
    }

    @Test
    void shouldUpdate() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(repository.save(any(Conta.class))).thenReturn(entity);

        Conta conta = service.update(ID, entity);

        verify(repository).findById(anyLong());
        verify(repository).save(any(Conta.class));

        assertEquals(conta.getId(), ID);
        assertEquals(conta.getVencimento(), VENCIMENTO);
        assertEquals(conta.getPagamento(), PAGAMENTO);
        assertEquals(conta.getValor(), VALOR);
        assertEquals(conta.getDescricao(), DESCRICAO);
        assertEquals(conta.getSituacao(), SITUACAO);
    }

    @Test
    void shouldFindByFilterWithFiltersNull() {
        PageRequest paged = PageRequest.of(0, 10);
        List<Conta> contaList = List.of(entity);
        Page<Conta> contaPage = new PageImpl<>(contaList, paged, contaList.size());

        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(contaPage);

        Page<Conta> contasPaged = service.findByFilter(null, null, pageable);

        verify(repository).findAll(any(Specification.class), any(Pageable.class));

        List<Conta> contas = contasPaged.stream().toList();

        assertEquals(contas.get(0).getId(), ID);
        assertEquals(contas.get(0).getVencimento(), VENCIMENTO);
        assertEquals(contas.get(0).getPagamento(), PAGAMENTO);
        assertEquals(contas.get(0).getValor(), VALOR);
        assertEquals(contas.get(0).getDescricao(), DESCRICAO);
        assertEquals(contas.get(0).getSituacao(), SITUACAO);
    }

    @Test
    void shouldFindByFilterWithFilterVencimento() {
        PageRequest paged = PageRequest.of(0, 10);
        List<Conta> contaList = List.of(entity);
        Page<Conta> contaPage = new PageImpl<>(contaList, paged, contaList.size());

        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(contaPage);

        Page<Conta> contasPaged = service.findByFilter(VENCIMENTO, null, pageable);

        verify(repository).findAll(any(Specification.class), any(Pageable.class));

        assertEquals(contasPaged.stream().toList().size(), 1);
    }

    @Test
    void shouldFindByFilterWithFilterDescricao() {
        PageRequest paged = PageRequest.of(0, 10);
        List<Conta> contaList = List.of(entity);
        Page<Conta> contaPage = new PageImpl<>(contaList, paged, contaList.size());

        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(contaPage);

        Page<Conta> contasPaged = service.findByFilter(null, DESCRICAO, pageable);

        verify(repository).findAll(any(Specification.class), any(Pageable.class));

        assertEquals(contasPaged.stream().toList().size(), 1);
    }

    @Test
    void shouldUpload() throws IOException {
        MockMultipartFile multipartFile = getCsvFile("src/test/resources/test.csv", "test.csv");

        service.upload(multipartFile);

        verify(repository).saveAll(anyList());
    }

    @Test
    void shouldThrowCsvInvalidFormatException() throws IOException {
        MockMultipartFile multipartFile = getCsvFile("src/test/resources/test.csv", "test.xls");

        assertThrows(CsvInvalidFormatException.class, () -> service.upload(multipartFile));
    }

    @Test
    void shouldThrowReadCsvErrorException() throws IOException {
        MockMultipartFile multipartFile = getCsvFile("src/test/resources/test-error.csv",
                "test-error.csv");

        assertThrows(ReadCsvErrorException.class, () -> service.upload(multipartFile));
    }


    @Test
    void shouldFindGastosByPeriodo() {
        GastosResponse gastosResponse = createGastosResponse();

        when(repository.findGastosByPeriodo(any(LocalDate.class), any(LocalDate.class))).thenReturn(gastosResponse);

        GastosResponse gastos = service.findGastosByPeriodo(PAGAMENTO, PAGAMENTO.plusDays(1L));

        verify(repository).findGastosByPeriodo(any(LocalDate.class), any(LocalDate.class));

        assertEquals(gastos.getValor(), VALOR);
    }

    @Test
    void shouldChangeSituacaoById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(repository.save(any(Conta.class))).thenReturn(entity);

        service.changeSituacaoById(ID, SituacaoEnum.A_PAGAR);

        verify(repository).findById(anyLong());
        verify(repository).save(any(Conta.class));
    }
}
