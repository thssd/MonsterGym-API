package com.monstergym.api.domain.treinadores;

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
