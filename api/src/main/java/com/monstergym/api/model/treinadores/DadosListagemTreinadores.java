package com.monstergym.api.model.treinadores;

public record DadosListagemTreinadores(Long id,
                                       String nome,
                                       String cref,
                                       String telefone,
                                       Especialidade especialidade) {
    public DadosListagemTreinadores (Treinador treinador){
        this(treinador.getId(),
                treinador.getNome(),
                treinador.getCref(),
                treinador.getTelefone(),
                treinador.getEspecialidade());
    }
}
