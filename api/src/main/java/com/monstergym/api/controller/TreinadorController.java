package com.monstergym.api.controller;

import com.monstergym.api.model.treinadores.DadosAtualizarTreinador;
import com.monstergym.api.model.treinadores.DadosListagemTreinadores;
import com.monstergym.api.model.treinadores.DadosTreinadores;
import com.monstergym.api.model.treinadores.Treinador;
import com.monstergym.api.repository.TreinadorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("treinadores")
public class TreinadorController {

    @Autowired
    TreinadorRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosTreinadores dadosTreinadores){
        repository.save(new Treinador(dadosTreinadores));
    }

    @GetMapping
    public Page<DadosListagemTreinadores> listar(@PageableDefault(sort = {"id"}) Pageable pageable){
        return repository.findAllByAtivoTrue(pageable).map(DadosListagemTreinadores::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizarTreinador dadosAtualizarTreinador){
        var carregarTreinador = repository.getReferenceById(dadosAtualizarTreinador.id());
        carregarTreinador.atualizarInformacoes(dadosAtualizarTreinador);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        var carregarTreinador = repository.getReferenceById(id);
        carregarTreinador.excluir();
    }
}
