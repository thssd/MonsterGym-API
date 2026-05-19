package com.monstergym.api.controller;

import com.monstergym.api.model.agendamento.Agendamento;
import com.monstergym.api.model.agendamento.DadosAgendamento;
import com.monstergym.api.model.agendamento.DadosListagemAgendamento;
import com.monstergym.api.repository.AgendamentoRepository;
import com.monstergym.api.service.AgendamentoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @Autowired
    private AgendamentoRepository repository;

    @PostMapping
    @Transactional
    public void agendar(@RequestBody @Valid DadosAgendamento dadosAgendamento){
        service.agendar(dadosAgendamento);
    }
}

