package com.monstergym.api.domain.aulas.validacoes;

import com.monstergym.api.domain.aulas.DadosAula;
import com.monstergym.api.infra.exceptions.ValidacaoException;
import com.monstergym.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAlunoAtivo implements IValidadorAula {

    @Autowired
    private AlunoRepository repository;

    public void validar(DadosAula dados){
        var alunoAtivo = repository.findAtivoById(dados.idAluno());

        if (!alunoAtivo){
            throw new ValidacaoException("O aluno está inátivo.");
        }
    }
}
