package com.monstergym.api.controller;

import com.monstergym.api.domain.alunos.DadosAvaliacaoAlunos;
import com.monstergym.api.domain.avaliacoes.DadosAvaliacao;
import com.monstergym.api.repository.AlunoRepository;
import com.monstergym.api.service.AvaliacaoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService service;

    @Autowired
    private AlunoRepository alunoRepository;

    @PutMapping("/alunos")
    @Transactional
    public ResponseEntity<DadosAvaliacao> avaliacao(@RequestBody DadosAvaliacaoAlunos dadosAlunos) {
        var carregarAluno = alunoRepository.getReferenceById(dadosAlunos.id());
        carregarAluno.realizarAvaliacao(dadosAlunos);

        var avaliacao = service.retornaResultado(dadosAlunos);

        return ResponseEntity.ok(avaliacao);
    }

}
