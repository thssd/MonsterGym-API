package com.monstergym.api.domain.aulas;

import java.time.LocalDateTime;

public record DadosDetalhamentoAula(Long id,
                                    Long idTreinador,
                                    Long idAluno,
                                    LocalDateTime data) {

    public DadosDetalhamentoAula(Long id, Long idTreinador, Long idAluno, LocalDateTime data) {
        this.id = id;
        this.idTreinador = idTreinador;
        this.idAluno = idAluno;
        this.data = data;
    }
}
