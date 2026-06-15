package com.monstergym.api.domain.aulas.cancelamentos;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoAula(@NotNull
                                    Long idConsulta,
                                    @NotNull
                                    MotivoCancelamento motivoCancelamento,
                                    String descricao) {
}
