package com.monstergym.api.service;

import com.monstergym.api.domain.estatisticas.DadosEstatisticaDTO;
import com.monstergym.api.domain.pagamentos.Pagamento;
import com.monstergym.api.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class EstatisticaService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public DadosEstatisticaDTO estatisticas() {
        List<Pagamento> dadosPagamentoList = pagamentoRepository.findAll();

        if (dadosPagamentoList.isEmpty()) {
            return new DadosEstatisticaDTO(0L, 0.0, 0.0, 0.0, 0.0);
        }

        DoubleSummaryStatistics dadosEstatisticas = dadosPagamentoList.stream()
                .mapToDouble(Pagamento::getValor)
                .summaryStatistics();

        return new DadosEstatisticaDTO(dadosEstatisticas.getCount(),
                dadosEstatisticas.getSum(),
                dadosEstatisticas.getAverage(),
                dadosEstatisticas.getMax(),
                dadosEstatisticas.getMin());
    }
}
