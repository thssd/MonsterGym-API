package com.monstergym.api.controller;

import com.monstergym.api.domain.pagamentos.DadosCancelamentoPagamento;
import com.monstergym.api.domain.pagamentos.DadosPagamento;
import com.monstergym.api.service.PagametoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagametoService service;

    @PostMapping
    public ResponseEntity efetuarPagamento(@RequestBody @Valid DadosPagamento dados) {
        var dto = service.aprovarPagamento(dados);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelarPagamento(@RequestBody @Valid DadosCancelamentoPagamento dadosCancelamento) {
        service.cancelarPagamento(dadosCancelamento);

        return ResponseEntity.noContent().build();
    }
}
