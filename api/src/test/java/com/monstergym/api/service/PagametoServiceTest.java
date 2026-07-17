package com.monstergym.api.service;

import com.monstergym.api.domain.alunos.Planos;
import com.monstergym.api.domain.pagamentos.DadosDetalhamentoPagamento;
import com.monstergym.api.domain.pagamentos.DadosPagamento;
import com.monstergym.api.domain.pagamentos.Pagamento;
import com.monstergym.api.domain.pagamentos.validacoes.IValidadorPagamento;
import com.monstergym.api.infra.exceptions.ValidacaoException;
import com.monstergym.api.repository.AlunoRepository;
import com.monstergym.api.repository.PagamentoRepository;
import net.bytebuddy.asm.Advice;
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
    private PagametoService pagametoService;

    @BeforeEach
    void setUp() {
        pagametoService = new PagametoService();
        ReflectionTestUtils.setField(pagametoService, "alunoRepository", alunoRepository);
        ReflectionTestUtils.setField(pagametoService, "pagamentoRepository", pagamentoRepository);
        ReflectionTestUtils.setField(pagametoService, "validadores", List.of(validador1, validador2));
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
                    pagametoService.aprovarPagamento(dados));

            verify(pagamentoRepository, never()).save(any());
        }

    }

    @Nested
    class cancelarPagamento {}
}