package com.monstergym.api.controller;

import com.monstergym.api.model.alunos.Aluno;
import com.monstergym.api.model.alunos.DadosAlunos;
import com.monstergym.api.model.alunos.DadosAtualizarAluno;
import com.monstergym.api.model.alunos.DadosListagemAluno;
import com.monstergym.api.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("alunos")
public class AlunosController {

    @Autowired
    AlunoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosAlunos dadosAlunos){
        repository.save(new Aluno(dadosAlunos));
    }

    @GetMapping
    public Page<DadosListagemAluno> listar(@PageableDefault(sort = {"cpf"}) Pageable pageable){
        return repository.findAllByAtivoTrue(pageable).map(DadosListagemAluno::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizarAluno dadosAtualizarAluno){
        var carregarAluno = repository.getReferenceById(dadosAtualizarAluno.id());
        carregarAluno.atualizarInformacoes(dadosAtualizarAluno);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        var carregarAluno = repository.getReferenceById(id);
        carregarAluno.excluir();
    }
}
