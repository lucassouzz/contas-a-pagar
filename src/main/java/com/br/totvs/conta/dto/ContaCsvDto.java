package com.br.totvs.conta.dto;

import com.br.totvs.conta.Conta;
import com.br.totvs.conta.enumeration.SituacaoEnum;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class ContaCsvDto {

    @CsvDate(value = "dd/MM/yyyy")
    @CsvBindByPosition(position = 0)
    private LocalDate vencimento;

    @CsvDate(value = "dd/MM/yyyy")
    @CsvBindByPosition(position = 1)
    private LocalDate pagamento;

    @CsvBindByPosition(position = 2)
    private BigDecimal valor;

    @CsvBindByPosition(position = 3)
    private String descricao;

    public static Conta toEntity(ContaCsvDto dto) {
        return Conta.builder()
                .vencimento(dto.getVencimento())
                .pagamento(dto.getPagamento())
                .valor(dto.getValor())
                .descricao(dto.getDescricao())
                .situacao(SituacaoEnum.A_PAGAR)
                .build();
    }
}
