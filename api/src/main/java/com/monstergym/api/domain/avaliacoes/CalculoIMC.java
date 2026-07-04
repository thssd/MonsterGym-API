package com.monstergym.api.domain.avaliacoes;

import com.monstergym.api.domain.alunos.DadosAvaliacaoAlunos;
import org.springframework.stereotype.Component;

@Component
public class CalculoIMC {

    public String calculoIMC (DadosAvaliacaoAlunos dadosAlunos) {
        var altura = dadosAlunos.altura();
        var peso = dadosAlunos.peso();

        var imc = peso / (altura * altura);

        if (imc < 18.5) {
            return "Abaixo do peso.";
        } else if (imc >= 18.5 && imc < 25) {
            return "Peso normal.";
        } else if (imc > 25 && imc < 30) {
            return "Sobrepeso.";
        } else if (imc > 30 && imc < 40) {
            return "Obesidade.";
        } else {
            return "Obesidade mórbida.";
        }
    }
}
