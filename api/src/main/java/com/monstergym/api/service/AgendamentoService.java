package com.monstergym.api.service;

import com.monstergym.api.model.agendamento.Agendamento;
import com.monstergym.api.model.agendamento.DadosAgendamento;
import com.monstergym.api.model.agendamento.Status;
import com.monstergym.api.repository.AgendamentoRepository;
import com.monstergym.api.repository.AlunoRepository;
import com.monstergym.api.repository.TreinadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private TreinadorRepository treinadorRepository;

    @Autowired
    private AlunoRepository alunoRepository;


    public void agendar(DadosAgendamento dadosAgendamento){
        boolean treinadorOcupado = agendamentoRepository.existsByIdAndData(
                            dadosAgendamento.treinadorId(),
                            dadosAgendamento.data());
        if (treinadorOcupado){
            throw new RuntimeException("O treinador já possui um agendamento nesse horário.");
        }

        var treinador = treinadorRepository.getReferenceById(dadosAgendamento.treinadorId());
        var aluno = alunoRepository.getReferenceById(dadosAgendamento.alunoId());

        Agendamento agendamento = new Agendamento(null, treinador, aluno, dadosAgendamento.data(),
                Status.AGENDADA);

        agendamentoRepository.save(agendamento);
    }
}
