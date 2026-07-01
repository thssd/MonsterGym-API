package com.monstergym.api.domain.pagamentos.validacoes;

import com.monstergym.api.domain.pagamentos.DadosPagamento;

public interface IValidadorPagamento {

    void validar(DadosPagamento dados);
}
