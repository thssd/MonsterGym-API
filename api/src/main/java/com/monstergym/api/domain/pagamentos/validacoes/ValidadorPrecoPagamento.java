package com.monstergym.api.domain.pagamentos.validacoes;

import com.monstergym.api.domain.pagamentos.DadosPagamento;
import com.monstergym.api.infra.exceptions.ValidacaoException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ValidadorPrecoPagamento implements IValidadorPagamento{

    public void validar(DadosPagamento dados) {
        if (!Objects.equals(dados.valor(), dados.plano().getValor())) {
                throw new ValidacaoException(
                        "O valor do plano " + dados.plano() + " deve ser R$ " + dados.plano().getValor());
        }
    }
}
