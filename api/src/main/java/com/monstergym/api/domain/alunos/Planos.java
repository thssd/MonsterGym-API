package com.monstergym.api.domain.alunos;

public enum Planos {

    PADRAO(100.00),
    PREMIUM(120.00),
    MONSTER(180.00);

    private final Double valor;

    Planos(Double valor) {
        this.valor = valor;
    }

    public Double getValor() {
        return valor;
    }
}
