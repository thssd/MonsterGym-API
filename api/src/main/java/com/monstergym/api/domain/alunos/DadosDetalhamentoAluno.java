package com.monstergym.api.domain.alunos;

import com.monstergym.api.domain.treinadores.Especialidade;

public record DadosDetalhamentoAluno(Long id,
                                     String nome,
                                     String email,
                                     String telefone,
                                     String cpf,
                                     Planos plano,
                                     Double altura,
                                     Double peso,
                                     Especialidade objetivo,
                                     Sexo sexo) {

    public DadosDetalhamentoAluno (Aluno aluno){
        this(aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getCpf(),
                aluno.getPlano(),
                aluno.getAltura(),
                aluno.getPeso(),
                aluno.getObjetivo(),
                aluno.getSexo());
    }
}
