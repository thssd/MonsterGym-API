package com.monstergym.api.service;

import com.monstergym.api.domain.alunos.DadosAvaliacaoAlunos;
import com.monstergym.api.domain.avaliacoes.CalculoIMC;
import com.monstergym.api.domain.avaliacoes.DadosAvaliacao;
import com.monstergym.api.domain.avaliacoes.EscolherEspecialidade;
import com.monstergym.api.domain.treinadores.DadosAvaliacaoTreinador;
import com.monstergym.api.domain.treinadores.Especialidade;
import com.monstergym.api.repository.TreinadorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvaliacaoServiceTest {

    @Mock
    private TreinadorRepository treinadorRepository;

    @Mock
    private CalculoIMC calculoIMC;

    @Mock
    private EscolherEspecialidade escolherEspecialidade;

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @Test
    @DisplayName("deve retornar DadosAvaliacao com sucesso " +
            "quando encontrar um treinador para a especialidade escolhida")
    void retornaResultado() {
        //arange
        Especialidade especialidadeEsperada = Especialidade.FUNCIONAL;

        DadosAvaliacaoTreinador dadosAvaliacaoTreinador = new DadosAvaliacaoTreinador(
                1L,
                "Nome Treinador",
                "123456789",
                especialidadeEsperada
        );

        DadosAvaliacaoAlunos dadosAvaliacaoAlunos = new DadosAvaliacaoAlunos(
                1L,
                1.75,
                80.0,
                especialidadeEsperada
        );

        String resultadoIMC = "Sobrepeso.";

        when(calculoIMC.calculoIMC(dadosAvaliacaoAlunos)).thenReturn(resultadoIMC);

        when(escolherEspecialidade.escoherEspecialidade(
                resultadoIMC,
                dadosAvaliacaoAlunos.objetivo())).thenReturn(especialidadeEsperada);

        when(treinadorRepository.findTopByEspecialidade(especialidadeEsperada))
                .thenReturn(Optional.of(dadosAvaliacaoTreinador));


        //act
        DadosAvaliacao resultado = avaliacaoService
                .retornaResultado(dadosAvaliacaoAlunos);

        //assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.resultado()).isEqualTo(resultadoIMC);
        assertThat(resultado.treinador()).isEqualTo(dadosAvaliacaoTreinador);

    }
}
