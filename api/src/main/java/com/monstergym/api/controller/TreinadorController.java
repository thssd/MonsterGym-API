package com.monstergym.api.controller;

import com.monstergym.api.domain.treinadores.DadosDetalhamentoTreinador;
import com.monstergym.api.domain.treinadores.DadosAtualizarTreinador;
import com.monstergym.api.domain.treinadores.DadosListagemTreinadores;
import com.monstergym.api.domain.treinadores.DadosTreinadores;
import com.monstergym.api.domain.treinadores.Treinador;
import com.monstergym.api.repository.TreinadorRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("treinadores")
@SecurityRequirement(name = "bearer-key")
public class TreinadorController {

    @Autowired
    TreinadorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody DadosTreinadores dadosTreinadores, UriComponentsBuilder uriComponentsBuilder){
        var treinador = new Treinador(dadosTreinadores);

        repository.save(treinador);

        var uri = uriComponentsBuilder.path("/treinador/{id}").buildAndExpand(treinador.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTreinador(treinador));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTreinadores>> listar(@PageableDefault(sort = {"id"}) Pageable pageable){
        var paginacao = repository.findAllByAtivoTrue(pageable).map(DadosListagemTreinadores::new);

        return ResponseEntity.ok(paginacao);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarTreinador dadosAtualizarTreinador){
        var carregarTreinador = repository.getReferenceById(dadosAtualizarTreinador.id());
        carregarTreinador.atualizarInformacoes(dadosAtualizarTreinador);

        return ResponseEntity.ok(new DadosDetalhamentoTreinador(carregarTreinador));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var carregarTreinador = repository.getReferenceById(id);
        carregarTreinador.excluir();

        return ResponseEntity.noContent().build();
    }
}
