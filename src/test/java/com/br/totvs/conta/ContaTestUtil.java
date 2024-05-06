package com.br.totvs.conta;

import com.br.totvs.conta.enumeration.SituacaoEnum;
import com.br.totvs.conta.payload.ContaRequest;
import com.br.totvs.conta.payload.GastosResponse;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ContaTestUtil {

    public static final Long ID = 1L;
    public static final LocalDate VENCIMENTO = LocalDate.of(2024, 10, 20);
    public static final LocalDate PAGAMENTO = LocalDate.of(2024, 10, 19);
    public static final BigDecimal VALOR = BigDecimal.valueOf(200.00);
    public static final String DESCRICAO = "Teste";
    public static final SituacaoEnum SITUACAO = SituacaoEnum.PAGO;

    public static Conta createConta() {
        return Conta.builder()
                .id(ID)
                .vencimento(VENCIMENTO)
                .pagamento(PAGAMENTO)
                .valor(VALOR)
                .descricao(DESCRICAO)
                .situacao(SITUACAO)
                .build();
    }

    public static ContaRequest createContaRequest() {
        return ContaRequest.builder()
                .vencimento(VENCIMENTO)
                .pagamento(PAGAMENTO)
                .valor(VALOR)
                .descricao(DESCRICAO)
                .situacao(SITUACAO)
                .build();
    }

    public static GastosResponse createGastosResponse() {
        return GastosResponse.builder()
                .valor(VALOR)
                .build();
    }

    public static MockMultipartFile getCsvFile(String filePath, String fileName) throws IOException {
        File file = new File(filePath);
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile(fileName, fileName, "text/csv", input);

        return multipartFile;
    }
}
