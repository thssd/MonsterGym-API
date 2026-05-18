package com.monstergym.api.model.treinadores;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarTreinador (@NotNull Long id,
                                       String nome,
                                       String telefone){
}
