package com.br.totvs.conta.repository;

import com.br.totvs.conta.Conta;
import com.br.totvs.conta.payload.GastosResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface ContaRepository extends JpaRepository<Conta, Long>, JpaSpecificationExecutor<Conta> {

    @Query("SELECT new com.br.totvs.conta.payload.GastosResponse(SUM(c.valor)) FROM Conta c WHERE " +
            "c.pagamento BETWEEN :dataInicial AND :dataFinal")
    GastosResponse findGastosByPeriodo(LocalDate dataInicial, LocalDate dataFinal);
}
