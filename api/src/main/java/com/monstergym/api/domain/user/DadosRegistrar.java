package com.monstergym.api.domain.user;

public record DadosRegistrar(String username,
                             String password,
                             UserRole role) {
}
