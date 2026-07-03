package com.monstergym.api.domain.alunos;

import com.monstergym.api.domain.treinadores.Especialidade;

public record DadosAlunos(String nome,
                          String email,
                          String telefone,
                          String cpf,
                          Planos plano,
                          Double altura,
                          Double peso,
                          Especialidade objetvo) {
}
