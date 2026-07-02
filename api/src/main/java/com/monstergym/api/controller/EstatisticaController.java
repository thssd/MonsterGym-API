package com.monstergym.api.controller;

import com.monstergym.api.domain.estatisticas.DadosEstatisticaDTO;
import com.monstergym.api.service.EstatisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {

    @Autowired
    private EstatisticaService service;

    @GetMapping
    public ResponseEntity<DadosEstatisticaDTO> exibirEstatisticas() {
        return ResponseEntity.ok(service.estatisticas());
    }
}
