package com.monstergym.api.domain.treinadores;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarTreinador (@NotNull Long id,
                                       String nome,
                                       String telefone){
}
