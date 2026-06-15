package com.monstergym.api.service;

import com.monstergym.api.domain.aulas.Aula;
import com.monstergym.api.domain.aulas.DadosAula;
import com.monstergym.api.domain.aulas.DadosDetalhamentoAula;
import com.monstergym.api.domain.aulas.cancelamentos.DadosCancelamentoAula;
import com.monstergym.api.domain.aulas.cancelamentos.MotivoCancelamento;
import com.monstergym.api.domain.aulas.validacoes.IValidadorAula;
import com.monstergym.api.domain.treinadores.Treinador;
import com.monstergym.api.infra.exceptions.ValidacaoException;
import com.monstergym.api.repository.AlunoRepository;
import com.monstergym.api.repository.AulaRepository;
import com.monstergym.api.repository.TreinadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private TreinadorRepository treinadorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private List<IValidadorAula> validadores;

    public DadosDetalhamentoAula agendar(DadosAula dados){
        if (!alunoRepository.existsById(dados.idAluno())){
            throw new ValidacaoException("Id do aluno informado não existe.");
        }
        
        if (dados.idTreinador() != null && !treinadorRepository.existsById(dados.idTreinador())){
            throw new ValidacaoException("Id do treinador informado não existe.");
        }

        validadores.forEach(v -> v.validar(dados));

        var aluno = alunoRepository.getReferenceById(dados.idAluno());
        var treinador = escolherTreinador(dados);
        var aula = new Aula(null, treinador, aluno, dados.data(), null, null, null);

        aulaRepository.save(aula);

        return new DadosDetalhamentoAula(aula);
    }

    private Treinador escolherTreinador(DadosAula dados) {
        if (dados.idTreinador() != null){
            return treinadorRepository.getReferenceById(dados.idTreinador());
        }

        if (dados.especialidade() == null){
            throw new ValidacaoException("A especialidade é obrigátoria quando nenhum médico é enviado.");
        }

        return treinadorRepository.escolherTreinadorAleatorio(dados.especialidade(), dados.data());
    }

    private void cancelar(DadosCancelamentoAula dados){
        if (!aulaRepository.existsById(dados.idConsulta())){
            throw new ValidacaoException("Id da consulta não informado ou não existe");
        }

        if (dados.motivoCancelamento() == null){
            throw new ValidacaoException("Por favor, informe o motivo do cancelamento.");
        }

        if (dados.motivoCancelamento() == MotivoCancelamento.OUTRO && dados.descricao() == null){
            throw new ValidacaoException("Informe o motivo do cancelamento.");
        }

        var aula = aulaRepository.getReferenceById(dados.idConsulta());
        aula.cancelar(dados.motivoCancelamento(), dados.descricao());
    }

}
