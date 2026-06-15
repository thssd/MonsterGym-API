package com.monstergym.api.domain.aulas.cancelamentos;

import com.monstergym.api.infra.exceptions.ValidacaoException;
import com.monstergym.api.repository.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioCancelamento implements IValidadorCancelamentoAula {

    @Autowired
    private AulaRepository repository;

    @Override
    public void cancelar(DadosCancelamentoAula dados) {
        var aula = repository.getReferenceById(dados.idConsulta());
        var horarioAtual = LocalDateTime.now();

        var diferenca = Duration.between(horarioAtual, aula.getData()).toHours();

        if (diferenca < 24){
            throw new ValidacaoException(
                    "Você somente pode cancelar uma aula com pelo menos 24 Horas de antecedência.");
        }
    }
}
