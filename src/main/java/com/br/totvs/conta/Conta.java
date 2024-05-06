package com.br.totvs.conta;

import com.br.totvs.conta.enumeration.SituacaoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conta_seq")
    @SequenceGenerator(name = "conta_seq", sequenceName = "conta_id_seq", allocationSize = 1)
    private Long id;
    private LocalDate vencimento;
    private LocalDate pagamento;
    private BigDecimal valor;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private SituacaoEnum situacao;
}
