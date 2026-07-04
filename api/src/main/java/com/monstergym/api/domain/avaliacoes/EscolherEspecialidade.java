package com.monstergym.api.domain.avaliacoes;

import com.monstergym.api.domain.treinadores.Especialidade;
import org.springframework.stereotype.Component;

@Component
public class EscolherEspecialidade {

    public Especialidade escoherEspecialidade(String imc, Especialidade objetivo) {
        if (imc.equals("Abaixo do peso.")) {
            return Especialidade.HIPERTROFIA;
        }
        if (imc.equals("Peso normal.")) {
            return objetivo;
        }
        if (imc.equals("Sobrepeso.")) {
            return Especialidade.FUNCIONAL;
        }
        if (imc.equals("Obesidade.")) {
            return Especialidade.EMAGRECIMENTO;
        }
        if (imc.equals("Obesidade mórbida.")) {
            return Especialidade.EMAGRECIMENTO;
        }

        return Especialidade.REABILITACAO;
    }
}
