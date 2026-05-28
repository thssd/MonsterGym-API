package com.monstergym.api.controller;

import com.monstergym.api.domain.user.DadosAutenticar;
import com.monstergym.api.domain.user.DadosRegistrar;
import com.monstergym.api.domain.user.DadosToken;
import com.monstergym.api.domain.user.User;
import com.monstergym.api.repository.UserRepository;
import com.monstergym.api.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService service;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid DadosAutenticar dadosAutenticar){
        var usuario = new UsernamePasswordAuthenticationToken(dadosAutenticar.username(), dadosAutenticar.password());
        var auth = this.manager.authenticate(usuario);

        var tokenService = service.gerarToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new DadosToken(tokenService));
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody @Valid DadosRegistrar dadosRegistrar){
        if (this.repository.findByUsername(dadosRegistrar.username()) != null) {
            return ResponseEntity.badRequest().build();
        } else {
            String encryptedPassword = new BCryptPasswordEncoder().encode(dadosRegistrar.password());
            User newUser = new User(dadosRegistrar.username(), encryptedPassword, dadosRegistrar.role());

            this.repository.save(newUser);
            return ResponseEntity.ok().build();
        }
    }
}
