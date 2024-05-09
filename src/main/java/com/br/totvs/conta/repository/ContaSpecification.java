package com.br.totvs.conta.repository;

import com.br.totvs.conta.Conta;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ContaSpecification {

    private static String VENCIMENTO = "vencimento";
    private static String DESCRICAO = "descricao";

    public static Specification<Conta> buildFilter(LocalDate vencimento, String descricao) {
        Specification<Conta> spec = Specification.where(null);

        if (vencimento != null) {
            spec = spec.and((root, query, builder) -> builder.equal(root.get(VENCIMENTO), vencimento));
        }

        if (descricao != null && !descricao.isEmpty()) {
            spec = spec.and((root, query, builder) -> builder.like(builder.upper(root.get(DESCRICAO)), "%" + descricao.toUpperCase() + "%"));
        }

        return spec;
    }
}
