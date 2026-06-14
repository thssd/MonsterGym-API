package com.monstergym.api.domain.aulas.validacoes;

import com.monstergym.api.domain.aulas.DadosAula;
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

        var treinador = repository.getReferenceById(dados.idTreinador());
        var ativo = treinador.getAtivo();

        if (!ativo){
            throw new RuntimeException("O treinador escolhido não está ativo.");
        }
    }
}
