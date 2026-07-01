package com.monstergym.api.domain.alunos;

public record DadosDetalhamentoAluno(Long id,
                                     String nome,
                                     String email,
                                     String telefone,
                                     String cpf,
                                     Planos plano) {

    public DadosDetalhamentoAluno (Aluno aluno){
        this(aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getCpf(),
                aluno.getPlano());
    }
}
