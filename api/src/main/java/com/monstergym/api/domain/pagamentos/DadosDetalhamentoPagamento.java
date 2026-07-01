package com.monstergym.api.domain.pagamentos;

import com.monstergym.api.domain.alunos.Planos;

import java.time.LocalDateTime;

public record DadosDetalhamentoPagamento (Long id,
                                          Long idAluno,
                                          Double valor,
                                          LocalDateTime dataHora,
                                          Planos plano){

    public DadosDetalhamentoPagamento(Pagamento pagamento) {
        this(pagamento.getId(), pagamento.idAluno(), pagamento.getValor(), pagamento.getDataHora(),
                pagamento.getPlano());
    }
}
