package com.monstergym.api.domain.aulas.validacoes;

import com.monstergym.api.domain.aulas.DadosAula;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia {

    public void agendar(DadosAula dados){
        var dataAula = dados.data();
        var dataAtual = LocalDateTime.now();

        var diferencaEmHoras = Duration.between(dataAula, dataAtual).toHours();

        if (diferencaEmHoras < 1){
            throw new RuntimeException("As aulas devem ser marcadas com pelo menos 1 hora de antecedência.");
        }
    }
}
