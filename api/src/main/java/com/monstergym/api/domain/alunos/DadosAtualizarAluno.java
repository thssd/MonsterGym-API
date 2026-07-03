package com.monstergym.api.domain.alunos;

import com.monstergym.api.domain.treinadores.Especialidade;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarAluno(@NotNull Long id,
                                  String nome,
                                  String email,
                                  String telefone,
                                  Planos plano,
                                  Double altura,
                                  Double peso,
                                  Especialidade objetivo) {
}
