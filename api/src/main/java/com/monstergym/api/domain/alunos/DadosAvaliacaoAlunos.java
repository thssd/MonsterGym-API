package com.monstergym.api.domain.alunos;

import com.monstergym.api.domain.treinadores.Especialidade;

public record DadosAvaliacaoAlunos(Long id,
                                   Double altura,
                                   Double peso,
                                   Especialidade objetivo) {

    public DadosAvaliacaoAlunos (Aluno aluno) {
        this(aluno.getId(), aluno.getAltura(), aluno.getPeso(), aluno.getObjetivo());
    }

}
