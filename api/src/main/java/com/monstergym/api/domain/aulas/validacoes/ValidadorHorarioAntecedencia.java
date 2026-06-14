package com.monstergym.api.domain.aulas.validacoes;

import com.monstergym.api.domain.aulas.DadosAula;
import com.monstergym.api.infra.exceptions.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements IValidadorAula {

    public void validar(DadosAula dados){
        var dataAula = dados.data();
        var dataAtual = LocalDateTime.now();

        var diferencaEmHoras = Duration.between(dataAtual, dataAula).toHours();

        if (diferencaEmHoras < 1){
            throw new ValidacaoException("As aulas devem ser marcadas com pelo menos 1 hora de antecedência.");
        }
    }
}
