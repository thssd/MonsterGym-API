package com.monstergym.api.domain.aulas.validacoes;

import com.monstergym.api.domain.aulas.DadosAula;
import com.monstergym.api.infra.exceptions.ValidacaoException;
import com.monstergym.api.repository.TreinadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTreinadorAtivo implements IValidadorAula {

    @Autowired
    private TreinadorRepository repository;

    public void validar(DadosAula dados){
        if (dados.idTreinador() == null){
            return;
        }

        var treinadorAtivo = repository.findAtivoById(dados.idTreinador());

        if (treinadorAtivo == null){
            throw new ValidacaoException("O treinador não foi encontrado.");
        }

        if (!treinadorAtivo){
            throw new ValidacaoException("O treinador escolhido não está ativo.");
        }
    }
}
