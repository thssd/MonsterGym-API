package com.monstergym.api.domain.alunos;

public record DadosAlunos(String nome,
                          String email,
                          String telefone,
                          String cpf,
                          Planos plano) {
}
