package com.br.totvs.conta.payload;

import com.br.totvs.conta.Conta;
import com.br.totvs.conta.enumeration.SituacaoEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ContaRequest {

    @NotNull(message = "O campo vencimento é obrigatório.")
    private LocalDate vencimento;
    @NotNull(message = "O campo pagamento é obrigatório.")
    private LocalDate pagamento;
    @NotNull(message = "O campo valor é obrigatório.")
    private BigDecimal valor;
    @NotNull(message = "O campo descrição é obrigatório.")
    private String descricao;
    @NotNull(message = "O campo situação é obrigatório.")
    private SituacaoEnum situacao;

    public static Conta toEntity(ContaRequest request) {
        return Conta.builder()
                .vencimento(request.getVencimento())
                .pagamento(request.getPagamento())
                .valor(request.getValor())
                .descricao(request.getDescricao())
                .situacao(request.getSituacao())
                .build();
    }
}
