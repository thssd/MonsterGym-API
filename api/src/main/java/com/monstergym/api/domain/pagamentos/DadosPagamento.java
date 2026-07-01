package com.monstergym.api.domain.pagamentos;

import com.monstergym.api.domain.alunos.Planos;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosPagamento(@NotNull Long idAluno,
                             @Valid Double valor,
                             LocalDateTime dataHora,
                             Planos plano) {
}
