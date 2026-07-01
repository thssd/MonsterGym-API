package com.monstergym.api.domain.pagamentos.validacoes;

import com.monstergym.api.domain.pagamentos.DadosPagamento;
import com.monstergym.api.infra.exceptions.ValidacaoException;
import com.monstergym.api.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidarUltimoPagamento implements IValidadorPagamento{

    @Autowired
    private PagamentoRepository repository;

    public void validar(DadosPagamento dados){
        var ultimoPagamento = repository.findTopByAlunoIdOrderByDataHoraDesc(dados.idAluno());

        if (ultimoPagamento.isPresent()) {
            var proximoPagamento = ultimoPagamento.get().getDataHora().plusDays(30);

            if (LocalDateTime.now().isBefore(proximoPagamento)) {
                throw new ValidacaoException("Você só pode realizar um pagamento a cada 30 dias.");
            }
        }
    }
}
