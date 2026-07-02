package com.monstergym.api.domain.estatisticas;

public record DadosEstatisticaDTO(Long count,
                                  Double sum,
                                  Double avg,
                                  Double max,
                                  Double min) {
}
