package com.monstergym.api.domain.aulas.validacoes;

import com.monstergym.api.domain.aulas.DadosAula;
import com.monstergym.api.infra.exceptions.ValidacaoException;
import org.springframework.stereotype.Component;

@Component
public class ValidarHorarioDasAulas implements IValidadorAula {

    public void validar(DadosAula dados){
        var dataAula = dados.data();

        var horaAbertura = dataAula.getHour() < 7;
        var horaFechamento = dataAula.getHour() > 22;

        if (horaAbertura || horaFechamento){
            throw new ValidacaoException("Não é permitido marcar um aula nesse horário.");
        }
    }
}
