package com.monstergym.api.domain.pagamentos;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoPagamento (@NotNull Long idPagamento) {
}
