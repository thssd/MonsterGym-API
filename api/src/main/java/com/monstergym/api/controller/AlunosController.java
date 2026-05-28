package com.monstergym.api.controller;

import com.monstergym.api.domain.alunos.*;
import com.monstergym.api.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("alunos")
public class AlunosController {

    @Autowired
    AlunoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody DadosAlunos dadosAlunos, UriComponentsBuilder componentsBuilder){
        var aluno = new Aluno(dadosAlunos);

        repository.save(aluno);

        var uri = componentsBuilder.path("/alunos/{id}").buildAndExpand(aluno.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoAluno(aluno));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemAluno>> listar(@PageableDefault(sort = {"cpf"}) Pageable pageable){
        var paginacao = repository.findAllByAtivoTrue(pageable).map(DadosListagemAluno::new);

        return ResponseEntity.ok(paginacao);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarAluno dadosAtualizarAluno){
        var carregarAluno = repository.getReferenceById(dadosAtualizarAluno.id());
        carregarAluno.atualizarInformacoes(dadosAtualizarAluno);

        return ResponseEntity.ok(new DadosDetalhamentoAluno(carregarAluno));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var carregarAluno = repository.getReferenceById(id);
        carregarAluno.excluir();

        return ResponseEntity.noContent().build();
    }
}
