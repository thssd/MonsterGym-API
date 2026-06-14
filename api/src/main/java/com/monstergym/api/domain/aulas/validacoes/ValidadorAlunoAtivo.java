package com.monstergym.api.domain.aulas.validacoes;

import com.monstergym.api.domain.aulas.DadosAula;
import com.monstergym.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAlunoAtivo implements IValidadorAula {

    @Autowired
    private AlunoRepository repository;

    public void validar(DadosAula dados){
        var aluno = repository.getReferenceById(dados.idAluno());
        var ativo = aluno.getAtivo();

        if (!ativo){
            throw new RuntimeException("O aluno está inátivo.");
        }
    }
}
