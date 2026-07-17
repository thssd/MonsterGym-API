package com.monstergym.api.service;

import com.monstergym.api.domain.alunos.Aluno;
import com.monstergym.api.domain.alunos.DadosAlunos;
import com.monstergym.api.domain.alunos.Planos;
import com.monstergym.api.domain.pagamentos.DadosPagamento;
import com.monstergym.api.domain.pagamentos.Pagamento;
import com.monstergym.api.domain.pagamentos.validacoes.IValidadorPagamento;
import com.monstergym.api.domain.treinadores.Especialidade;
import com.monstergym.api.infra.exceptions.ValidacaoException;
import com.monstergym.api.repository.AlunoRepository;
import com.monstergym.api.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagametoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private IValidadorPagamento validador1;

    @Mock
    private IValidadorPagamento validador2;

    @InjectMocks
    private PagamentoService pagamentoService;

    @BeforeEach
    void setUp() {
        pagamentoService = new PagamentoService();
        ReflectionTestUtils.setField(pagamentoService, "alunoRepository", alunoRepository);
        ReflectionTestUtils.setField(pagamentoService, "pagamentoRepository", pagamentoRepository);
        ReflectionTestUtils.setField(pagamentoService, "validadores", List.of(validador1, validador2));
    }

    @Nested
    class aprovarPagamento {

        @Test
        @DisplayName("deve lançar exceção quando aluno não existe")
        void aprovarPagamento_caso1() {
            //arrange
            var dados = new DadosPagamento(
                    1L,
                    100.0,
                    LocalDateTime.now(),
                    Planos.PADRAO
            );

            when(alunoRepository.existsById(dados.idAluno())).thenReturn(false);

            //act & assert
            assertThrows(ValidacaoException.class, () ->
                    pagamentoService.aprovarPagamento(dados));

            verify(pagamentoRepository, never()).save(any());
        }

        @Test
        @DisplayName("deve aprovar pagamento quando dados validos")
        void aprovarPagamento_caso2() {
            //arrange
            var dados = new DadosPagamento(
                    1L,
                    100.0,
                    LocalDateTime.now(),
                    Planos.PADRAO
            );

            Aluno aluno = new Aluno(new DadosAlunos(
                    "Aluno",
                    "aluno@email",
                    "11888888888",
                    "12345678901",
                    Planos.PADRAO,
                    1.80,
                    80.0,
                    Especialidade.FUNCIONAL)
            );
            when(alunoRepository.existsById(dados.idAluno())).thenReturn(true);

            when(alunoRepository.getReferenceById(dados.idAluno())).thenReturn(aluno);


            //act
            var output = pagamentoService.aprovarPagamento(dados);

            //assert
            assertThat(output).isNotNull();

            verify(pagamentoRepository, times(1)).save(any(Pagamento.class));
            verify(validador1).validar(dados);
            verify(validador2).validar(dados);

        }

        @Test
        @DisplayName("deve lançar exception quando validador1 rejeita")
        void aprovarPagamento_caso3() {
            //arrange
            var dados = new DadosPagamento(
                    1L,
                    100.0,
                    LocalDateTime.now(),
                    Planos.PADRAO
            );

            Aluno aluno = new Aluno(new DadosAlunos(
                    "Aluno",
                    "aluno@email",
                    "11888888888",
                    "12345678901",
                    Planos.PADRAO,
                    1.80,
                    80.0,
                    Especialidade.FUNCIONAL)
            );
            when(alunoRepository.existsById(dados.idAluno())).thenReturn(true);

            when(alunoRepository.getReferenceById(dados.idAluno())).thenReturn(aluno);

            //act
            doThrow(new ValidacaoException("O valor do plano "
                    + dados.plano() + " deve ser R$ " + dados.plano().getValor()))
                    .when(validador1).validar(dados);

            //assert
            assertThrows(ValidacaoException.class, () ->
                    pagamentoService.aprovarPagamento(dados));

            verify(pagamentoRepository, never()).save(any());

        }
    }

    @Nested
    class cancelarPagamento {}
}