package com.monstergym.api.service;

import com.monstergym.api.domain.alunos.Aluno;
import com.monstergym.api.domain.aulas.Aula;
import com.monstergym.api.domain.aulas.DadosAula;
import com.monstergym.api.domain.aulas.cancelamentos.IValidadorCancelamentoAula;
import com.monstergym.api.domain.aulas.validacoes.IValidadorAula;
import com.monstergym.api.domain.treinadores.Especialidade;
import com.monstergym.api.domain.treinadores.Treinador;
import com.monstergym.api.infra.exceptions.ValidacaoException;
import com.monstergym.api.repository.AlunoRepository;
import com.monstergym.api.repository.AulaRepository;
import com.monstergym.api.repository.TreinadorRepository;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AulaServiceTest {

    @InjectMocks
    private AulaService aulaService;

    @Mock
    private AulaRepository aulaRepository;

    @Mock
    private TreinadorRepository treinadorRepository;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private IValidadorAula validadorAula;

    @Mock
    private IValidadorCancelamentoAula validadorCancelamento;

    @BeforeEach
    void setUp() {
        aulaService = new AulaService();
        ReflectionTestUtils.setField(aulaService, "aulaRepository", aulaRepository);
        ReflectionTestUtils.setField(aulaService, "treinadorRepository", treinadorRepository);
        ReflectionTestUtils.setField(aulaService, "alunoRepository", alunoRepository);
        ReflectionTestUtils.setField(aulaService, "validadores", List.of(validadorAula));
        ReflectionTestUtils.setField(aulaService, "canceladores", List.of(validadorCancelamento));
    }

    @Nested
    class agendar {

        @Test
        @DisplayName("deve lançar exception quando o id do aluno " +
                "informado não existe")
        void agendar_caso1() {
            //arrange
            var dadosAula = new DadosAula(
                    1L,
                    99L,
                    LocalDateTime.now(),
                    Especialidade.EMAGRECIMENTO
            );

            when(alunoRepository.existsById(dadosAula.idAluno()))
                    .thenReturn(false);

            //act && assert
            assertThrows(ValidacaoException.class, () ->
                    aulaService.agendar(dadosAula));
            verify(aulaRepository, never()).save(any());
        }

        @Test
        @DisplayName("deve lançar exception quando id do treinador for informado" +
                "e estiver inválido")
        void agendar_caso2() {
            //arrange
            var dadosAula = new DadosAula(
                    1L,
                    99L,
                    LocalDateTime.now(),
                    Especialidade.EMAGRECIMENTO
            );


            when(treinadorRepository.existsById(dadosAula.idTreinador()))
                    .thenReturn(false);

            when(alunoRepository.existsById(dadosAula.idAluno()))
                    .thenReturn(true);

            //act & assert
            var exception = assertThrows(ValidacaoException.class, () ->
                    aulaService.agendar(dadosAula));

            assertEquals("Id do treinador informado não existe.",
                    exception.getMessage());
        }

        @Test
        @DisplayName("deve escolher treinador aleatorio quando idTeinador é nulo" +
                " e especialidade é informada")
        void agendar_caso3() {
            //arrange
            var data = LocalDateTime.now().plusDays(1);
            var dadosAula = new DadosAula(
                    null,
                    10L,
                    data,
                    Especialidade.HIPERTROFIA
            );
            var treinador = mock(Treinador.class);

            when(alunoRepository.existsById(dadosAula.idAluno()))
                    .thenReturn(true);
            when(alunoRepository.getReferenceById(dadosAula.idAluno()))
                    .thenReturn(mock(Aluno.class));
            when(treinadorRepository
                    .escolherTreinadorAleatorio(Especialidade.HIPERTROFIA, data))
                    .thenReturn(treinador);

            //act
            aulaService.agendar(dadosAula);

            //assert
            verify(treinadorRepository)
                    .escolherTreinadorAleatorio(Especialidade.HIPERTROFIA, data);
        }

        @Test
        @DisplayName("deve acionar todos os validadores de aula")
        void chamaValidadores() {
            //arrange
            var dadosAula = new DadosAula(
                    1L,
                    99L,
                    LocalDateTime.now(),
                    Especialidade.EMAGRECIMENTO
            );

            when(alunoRepository.existsById(dadosAula.idAluno()))
                    .thenReturn(true);
            when(treinadorRepository.existsById(dadosAula.idTreinador()))
                    .thenReturn(true);
            when(alunoRepository.getReferenceById(dadosAula.idAluno()))
                    .thenReturn(mock(Aluno.class));
            when(treinadorRepository.getReferenceById(dadosAula.idTreinador()))
                    .thenReturn(mock(Treinador.class));

            //act
            aulaService.agendar(dadosAula);

            //assert
            verify(treinadorRepository).getReferenceById(dadosAula.idTreinador());
            verify(treinadorRepository, never())
                    .escolherTreinadorAleatorio(any(), any());
        }

        @Test
        @DisplayName("deve lançar exception quando" +
                " id treinador e epecialidade são nulos")
        void semTreinadorNemEspecialidade() {
            //arrange
            var dadosAula = new DadosAula(
                    null,
                    99L,
                    LocalDateTime.now(),
                    null
            );
            when(alunoRepository.existsById(dadosAula.idAluno()))
                    .thenReturn(true);
            when(alunoRepository.getReferenceById(dadosAula.idAluno()))
                    .thenReturn(mock(Aluno.class));

            //act & assert
            assertThatThrownBy(() ->
                    aulaService.agendar(dadosAula))
                    .isInstanceOf(ValidacaoException.class)
                    .hasMessageContaining("A especialidade é obrigátoria quando nenhum médico é enviado.");

        }

        @Test
        @DisplayName("deve escolher treinador aleatorio quando idTreinador é" +
                "nulo e especialidade for informada")
        void escolherTreinadorAleatorio() {
            //arrange
            var especialidade = Especialidade.HIPERTROFIA;
            var data = LocalDateTime.now();

            var dadosAula = new DadosAula(
                    null,
                    99L,
                    data,
                    especialidade
            );
            var treinador = mock(Treinador.class);

            when(alunoRepository.existsById(dadosAula.idAluno()))
                    .thenReturn(true);

            when(alunoRepository.getReferenceById(dadosAula.idAluno()))
                    .thenReturn(mock(Aluno.class));

            when(treinadorRepository.escolherTreinadorAleatorio(especialidade, data))
                    .thenReturn(treinador);

            //act
            aulaService.agendar(dadosAula);

            //assert
            verify(treinadorRepository).escolherTreinadorAleatorio(especialidade, data);
        }

        @Test
        @DisplayName("deve salvar aula e retornar os dados de detalhamento")
        void fluxoFeliz() {
            //arrange
            var especialidade = Especialidade.HIPERTROFIA;
            var data = LocalDateTime.now();

            var dadosAula = new DadosAula(
                    1L,
                    99L,
                    data,
                    especialidade
            );
            var treinador = mock(Treinador.class);

            when(alunoRepository.existsById(dadosAula.idAluno()))
                    .thenReturn(true);

            when(alunoRepository.getReferenceById(dadosAula.idAluno()))
                    .thenReturn(mock(Aluno.class));

            when(treinadorRepository.existsById(dadosAula.idTreinador()))
                    .thenReturn(true);

            when(treinadorRepository.getReferenceById(dadosAula.idTreinador()))
                    .thenReturn(treinador);

            //act
            var output = aulaService.agendar(dadosAula);

            //assert
            assertNotNull(output);
            verify(aulaRepository).save(any(Aula.class));
        }
    }

    @Nested
    class cancelar {

        @Test
        @DisplayName("deve lançar exception quando id do aluno não existe")
        void cancelar_caso1() {
            //arrange

            //act

            //assert
        }
    }
}