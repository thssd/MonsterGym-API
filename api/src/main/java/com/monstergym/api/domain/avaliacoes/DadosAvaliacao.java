package com.monstergym.api.domain.avaliacoes;

import com.monstergym.api.domain.treinadores.DadosAvaliacaoTreinador;

public record DadosAvaliacao(String resultado,
                             DadosAvaliacaoTreinador treinador) {
}
