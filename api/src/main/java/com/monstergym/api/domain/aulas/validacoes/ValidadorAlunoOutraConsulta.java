package com.monstergym.api.domain.aulas.validacoes;

import com.monstergym.api.domain.aulas.DadosAula;
import com.monstergym.api.repository.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAlunoOutraConsulta implements IValidadorAula {

    @Autowired
    private AulaRepository repository;

    public void validar(DadosAula dados){
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(22);

        var outraAulaMesmoDia = repository.existsByAlunoIdAndDataBetween(dados.idAluno(), primeiroHorario,
                ultimoHorario);

        if (outraAulaMesmoDia){
            throw new RuntimeException("O aluno já possui outra aula no dia.");
        }
    }
}
