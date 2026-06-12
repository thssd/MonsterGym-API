package com.monstergym.api.service;

import com.monstergym.api.domain.aulas.AulaRepository;
import com.monstergym.api.domain.aulas.DadosAula;
import com.monstergym.api.domain.aulas.DadosDetalhamentoAula;
import com.monstergym.api.domain.treinadores.Treinador;
import com.monstergym.api.repository.AlunoRepository;
import com.monstergym.api.repository.TreinadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private TreinadorRepository treinadorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public DadosDetalhamentoAula agendar(DadosAula dados){
        if (!alunoRepository.existsById(dados.idAluno())){
            throw new RuntimeException("Id do aluno informado não existe.");
        }
        
        if (dados.idTreinador() != null && !treinadorRepository.existsById(dados.idTreinador())){
            throw new RuntimeException("Id do treinador informado não existe.");
        }
        
        var aluno = alunoRepository.getReferenceById(dados.idAluno());
        var treinador = escolherTreinador(dados);
                    
    }

    private Treinador escolherTreinador(DadosAula dados) {
        if (dados.idTreinador() != null){
            return treinadorRepository.getReferenceById(dados.idTreinador());
        }

        if (dados.especialidade() == null){
            throw new RuntimeException("A especialidade é obrigátoria quando nenhum médico é enviado.");
        }

        return treinadorRepository.escolherTreinadorAleatorio(dados.especialidade(), dados.data());
    }

}
