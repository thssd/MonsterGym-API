package com.monstergym.api.domain.aulas;

import java.time.LocalDateTime;

public record DadosDetalhamentoAula(Long id,
                                    Long idTreinador,
                                    Long idAluno,
                                    LocalDateTime data) {

    public DadosDetalhamentoAula(Aula aula) {
        this(aula.getId(), aula.getTreinador().getId(), aula.getAluno().getId(), aula.getData());
    }

}
