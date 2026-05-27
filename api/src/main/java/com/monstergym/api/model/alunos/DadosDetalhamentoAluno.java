package com.monstergym.api.model.alunos;

public record DadosDetalhamentoAluno(Long id,
                                     String nome,
                                     String email,
                                     String telefone,
                                     String cpf) {

    public DadosDetalhamentoAluno (Aluno aluno){
        this(aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getCpf());
    }
}
