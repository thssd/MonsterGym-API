package com.monstergym.api.model.alunos;

public record DadosListagemAluno(Long id,
                                 String nome,
                                 String email,
                                 String telefone,
                                 String cpf) {

    public DadosListagemAluno (Aluno aluno){
        this(aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getCpf());
    }
}
