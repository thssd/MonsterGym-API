package com.monstergym.api.domain.alunos;

public record DadosListagemAluno(Long id,
                                 String nome,
                                 String email,
                                 String telefone,
                                 String cpf,
                                 Planos plano) {

    public DadosListagemAluno (Aluno aluno){
        this(aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getCpf(),
                aluno.getPlano());
    }
}
