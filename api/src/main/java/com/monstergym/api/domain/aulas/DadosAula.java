package com.monstergym.api.domain.aulas;

import com.monstergym.api.domain.treinadores.Especialidade;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAula(Long idTreinador,
                        @NotNull
                            Long idAluno,
                        @Future
                            LocalDateTime data,
                        Especialidade especialidade) {
}
