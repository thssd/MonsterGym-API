package com.monstergym.api.model.agendamento;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamento(@NotNull Long treinadorId,
                               @NotNull Long alunoId,
                               @NotNull LocalDateTime data) {
}
