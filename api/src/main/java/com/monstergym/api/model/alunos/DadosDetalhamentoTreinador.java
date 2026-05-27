package com.monstergym.api.model.alunos;

import com.monstergym.api.model.treinadores.Especialidade;
import com.monstergym.api.model.treinadores.Treinador;

public record DadosDetalhamentoTreinador(Long id,
                                         String nome,
                                         String cref,
                                         String telefone,
                                         Especialidade especialidade) {
    public DadosDetalhamentoTreinador(Treinador treinador){
        this(treinador.getId(),
                treinador.getNome(),
                treinador.getCref(),
                treinador.getTelefone(),
                treinador.getEspecialidade());
    }
}
