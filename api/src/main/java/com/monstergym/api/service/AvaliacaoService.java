package com.monstergym.api.service;

import com.monstergym.api.domain.alunos.DadosAvaliacaoAlunos;
import com.monstergym.api.domain.avaliacoes.CalculoIMC;
import com.monstergym.api.domain.avaliacoes.DadosAvaliacao;
import com.monstergym.api.domain.avaliacoes.EscolherEspecialidade;
import com.monstergym.api.domain.treinadores.DadosAvaliacaoTreinador;
import com.monstergym.api.domain.treinadores.Especialidade;
import com.monstergym.api.infra.exceptions.ValidacaoException;
import com.monstergym.api.repository.TreinadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

    @Autowired
    private TreinadorRepository treinadorRepository;

    @Autowired
    private CalculoIMC calculoIMC;

    @Autowired
    private EscolherEspecialidade escoherEspecialidade;

    public DadosAvaliacao retornaResultado(DadosAvaliacaoAlunos dadosAlunos) {

        String resultado = calculoIMC.calculoIMC(dadosAlunos);

        Especialidade especialidade = escoherEspecialidade
                .escoherEspecialidade(resultado, dadosAlunos.objetivo());

        DadosAvaliacaoTreinador treinador = treinadorRepository
                .findTopByEspecialidade(especialidade)
                .orElseThrow(() ->
                        new ValidacaoException("Nenhum treinador foi encontrado"));

        return new DadosAvaliacao(resultado, treinador);
    }

}
