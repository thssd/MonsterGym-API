package com.monstergym.api.controller;

import com.monstergym.api.domain.aulas.DadosAula;
import com.monstergym.api.service.AulaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("aulas")
@SecurityRequirement(name = "bearer-key")
public class AulaController {

    @Autowired
    private AulaService service;

    @PostMapping
    @Transactional
    public ResponseEntity agendamentos(@RequestBody @Valid DadosAula dados){
        var dto = service.agendar(dados);

        return ResponseEntity.ok(dto);
    }
}
