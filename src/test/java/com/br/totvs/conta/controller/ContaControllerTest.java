package com.br.totvs.conta.controller;

import com.br.totvs.conta.Conta;
import com.br.totvs.conta.enumeration.SituacaoEnum;
import com.br.totvs.conta.payload.ContaRequest;
import com.br.totvs.conta.payload.GastosResponse;
import com.br.totvs.conta.service.ContaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.br.totvs.conta.ContaTestUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ContaController.class)
class ContaControllerTest {

    private final Conta entity = createConta();
    private final ContaRequest request = createContaRequest();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private ContaService service;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void shouldCreate() throws Exception {
        when(service.save(any())).thenReturn(entity);

        String payload = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.vencimento").value(request.getVencimento().toString()))
                .andExpect(jsonPath("$.pagamento").value(request.getPagamento().toString()))
                .andExpect(jsonPath("$.valor").value(request.getValor()))
                .andExpect(jsonPath("$.descricao").value(request.getDescricao()))
                .andExpect(jsonPath("$.situacao").value(request.getSituacao().toString()));
    }

    @Test
    void shoulUpdate() throws Exception {
        when(service.update(any(), any())).thenReturn(entity);

        String payload = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/conta/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.vencimento").value(request.getVencimento().toString()))
                .andExpect(jsonPath("$.pagamento").value(request.getPagamento().toString()))
                .andExpect(jsonPath("$.valor").value(request.getValor()))
                .andExpect(jsonPath("$.descricao").value(request.getDescricao()))
                .andExpect(jsonPath("$.situacao").value(request.getSituacao().toString()));
    }

    @Test
    void shoulChangeSituacaoById() throws Exception {
        when(service.changeSituacaoById(any(), any())).thenReturn(entity);

        mockMvc.perform(put("/conta/{id}/alterar/{situacao}", ID, SituacaoEnum.A_PAGAR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetByFilterWithFiltersNull() throws Exception {
        PageRequest paged = PageRequest.of(0, 10);
        List<Conta> contaList = List.of(entity);
        Page<Conta> contaPage = new PageImpl<>(contaList, paged, contaList.size());

        when(service.findByFilter(any(), any(), any(Pageable.class))).thenReturn(contaPage);

        mockMvc.perform(get("/conta/filtrar")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(entity.getId()))
                .andExpect(jsonPath("$.content[0].vencimento").value(entity.getVencimento().toString()))
                .andExpect(jsonPath("$.content[0].pagamento").value(entity.getPagamento().toString()))
                .andExpect(jsonPath("$.content[0].valor").value(entity.getValor()))
                .andExpect(jsonPath("$.content[0].descricao").value(entity.getDescricao()))
                .andExpect(jsonPath("$.content[0].situacao").value(entity.getSituacao().toString()));
    }

    @Test
    void shouldGetByFilterWithFilterVencimento() throws Exception {
        PageRequest paged = PageRequest.of(0, 10);
        List<Conta> contaList = List.of(entity);
        Page<Conta> contaPage = new PageImpl<>(contaList, paged, contaList.size());

        when(service.findByFilter(any(), any(), any(Pageable.class))).thenReturn(contaPage);

        mockMvc.perform(get("/conta/filtrar")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .param("vencimento", VENCIMENTO.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(entity.getId()))
                .andExpect(jsonPath("$.content[0].vencimento").value(entity.getVencimento().toString()))
                .andExpect(jsonPath("$.content[0].pagamento").value(entity.getPagamento().toString()))
                .andExpect(jsonPath("$.content[0].valor").value(entity.getValor()))
                .andExpect(jsonPath("$.content[0].descricao").value(entity.getDescricao()))
                .andExpect(jsonPath("$.content[0].situacao").value(entity.getSituacao().toString()));
    }

    @Test
    void shouldGetByFilterWithFilterDescricao() throws Exception {
        PageRequest paged = PageRequest.of(0, 10);
        List<Conta> contaList = List.of(entity);
        Page<Conta> contaPage = new PageImpl<>(contaList, paged, contaList.size());

        when(service.findByFilter(any(), any(), any(Pageable.class))).thenReturn(contaPage);

        mockMvc.perform(get("/conta/filtrar")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .param("descricao", "Test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(entity.getId()))
                .andExpect(jsonPath("$.content[0].vencimento").value(entity.getVencimento().toString()))
                .andExpect(jsonPath("$.content[0].pagamento").value(entity.getPagamento().toString()))
                .andExpect(jsonPath("$.content[0].valor").value(entity.getValor()))
                .andExpect(jsonPath("$.content[0].descricao").value(entity.getDescricao()))
                .andExpect(jsonPath("$.content[0].situacao").value(entity.getSituacao().toString()));
    }

    @Test
    void shouldGetById() throws Exception {
        when(service.findById(ID)).thenReturn(entity);

        mockMvc.perform(get("/conta/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.vencimento").value(VENCIMENTO.toString()))
                .andExpect(jsonPath("$.pagamento").value(PAGAMENTO.toString()))
                .andExpect(jsonPath("$.valor").value(VALOR))
                .andExpect(jsonPath("$.descricao").value(DESCRICAO))
                .andExpect(jsonPath("$.situacao").value(SITUACAO.toString()));
    }

    @Test
    void shouldFindGastosByPeriodo() throws Exception {
        GastosResponse gastosResponse = createGastosResponse();
        when(service.findGastosByPeriodo(any(), any())).thenReturn(gastosResponse);

        mockMvc.perform(get("/conta/valor_gasto")
                        .param("dataInicial", PAGAMENTO.toString())
                        .param("dataFinal", PAGAMENTO.plusDays(1L).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valor").value(VALOR));
    }

    @Test
    void shouldUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv",
                MediaType.TEXT_PLAIN_VALUE, "test".getBytes());

        doNothing().when(service).upload(any(MultipartFile.class));

        mockMvc.perform(multipart("/conta/upload")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        verify(service).upload(any(MultipartFile.class));
    }
}
