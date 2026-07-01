package com.monstergym.api.service;

import com.monstergym.api.domain.pagamentos.DadosCancelamentoPagamento;
import com.monstergym.api.domain.pagamentos.DadosDetalhamentoPagamento;
import com.monstergym.api.domain.pagamentos.DadosPagamento;
import com.monstergym.api.domain.pagamentos.Pagamento;
import com.monstergym.api.domain.pagamentos.validacoes.IValidadorPagamento;
import com.monstergym.api.infra.exceptions.ValidacaoException;
import com.monstergym.api.repository.AlunoRepository;
import com.monstergym.api.repository.PagamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagametoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private List<IValidadorPagamento> validadores;

    public DadosDetalhamentoPagamento aprovarPagamento(DadosPagamento dados) {
        if (!alunoRepository.existsById(dados.idAluno())){
            throw new ValidacaoException("Id do aluno informado não existe.");
        }
        var dataHora = LocalDateTime.now();

        var aluno = alunoRepository.getReferenceById(dados.idAluno());
        var pagamento = new Pagamento(null, dados.valor(), dataHora, dados.plano(), aluno);

        validadores.forEach(v -> v.validar(dados));

        pagamentoRepository.save(pagamento);

        return new DadosDetalhamentoPagamento(pagamento);
    }

    @Transactional
    public void cancelarPagamento(DadosCancelamentoPagamento dados) {
        if (!pagamentoRepository.existsById(dados.idPagamento())) {
            throw new ValidacaoException("Pagamento não encontrado.");
        }

        pagamentoRepository.deleteById(dados.idPagamento());
    }
}
