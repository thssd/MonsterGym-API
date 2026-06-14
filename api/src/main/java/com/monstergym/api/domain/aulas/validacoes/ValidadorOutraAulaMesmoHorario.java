package com.monstergym.api.domain.aulas.validacoes;

import com.monstergym.api.domain.aulas.DadosAula;
import com.monstergym.api.repository.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorOutraAulaMesmoHorario implements IValidadorAula {

    @Autowired
    private AulaRepository repository;

    public void validar(DadosAula dados){
        var consultaTreinadorOutroHorario = repository.existsByTreinadorIdAndData(dados.idTreinador(), dados.data());

        if (consultaTreinadorOutroHorario){
            throw new RuntimeException("O treinador já possui outra aula nesse horário.");
        }
    }
}
