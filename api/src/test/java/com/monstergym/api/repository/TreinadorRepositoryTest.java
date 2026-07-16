package com.monstergym.api.repository;

import com.monstergym.api.domain.alunos.Aluno;
import com.monstergym.api.domain.alunos.DadosAlunos;
import com.monstergym.api.domain.alunos.Planos;
import com.monstergym.api.domain.aulas.Aula;
import com.monstergym.api.domain.treinadores.DadosTreinadores;
import com.monstergym.api.domain.treinadores.Especialidade;
import com.monstergym.api.domain.treinadores.Treinador;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TreinadorRepositoryTest {

    @Autowired
    private TreinadorRepository treinadorRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    //TESTE 1
    @Test
    @DisplayName("Deve devolver null quando nenhum treinador disponível na data.")
    void escolherTreinadorAleatorio_Caso1() {
        //given
        var segundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var treinador = cadastrarTreinador(
                "Treinador",
                "123456",
                "11999999999",
                Especialidade.EMAGRECIMENTO);

        var aluno = cadastrarAluno(
                "Aluno",
                "aluno@email",
                "11888888888",
                "12345678901",
                Planos.PADRAO,
                1.80,
                80.0,
                Especialidade.FUNCIONAL);

        cadastrarAula(treinador, aluno, segundaAs10);

        //when
        var treinadorLivre = treinadorRepository.escolherTreinadorAleatorio(Especialidade.EMAGRECIMENTO,
                segundaAs10);

        //then
        assertThat(treinadorLivre).isNull();
    }

    //TESTE 2
    @Test
    @DisplayName("Deve devolver treinador quando ele estiver disponível na data.")
    void escolherTreinadorAleatorio_Caso2() {
        //given
        var segundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var treinador = cadastrarTreinador(
                "Treinador",
                "123456",
                "11999999999",
                Especialidade.EMAGRECIMENTO);

        //when
        var treinadorLivre = treinadorRepository.escolherTreinadorAleatorio(Especialidade.EMAGRECIMENTO,
                segundaAs10);

        //then
        assertThat(treinadorLivre).isEqualTo(treinador);
    }

    //OBJETOS
    private void cadastrarAula(Treinador treinador, Aluno aluno, LocalDateTime data) {
        testEntityManager.persist(new Aula(null, treinador, aluno, data));
    }

    private Treinador cadastrarTreinador(String nome, String cref, String telefone, Especialidade especialidade) {
        var treinador = new Treinador(new DadosTreinadores(nome, cref, telefone, especialidade));
        testEntityManager.persist(treinador);
        return treinador;
    }

    private Aluno cadastrarAluno(
            String nome,
            String email,
            String telefone,
            String cpf,
            Planos planos,
            Double altura,
            Double peso,
            Especialidade objetivo) {

        var aluno = new Aluno(new DadosAlunos(nome,
                email,
                telefone,
                cpf,
                planos,
                altura,
                peso,
                objetivo));

        testEntityManager.persist(aluno);
        return aluno;
    }

    private DadosTreinadores dadosTreinadores(String nome, String cref, String telefone, Especialidade especialidade) {
        return new DadosTreinadores(
                nome,
                cref,
                telefone,
                especialidade
        );
    }

    private DadosAlunos dadosAlunos(String nome,
                                    String email,
                                    String telefone,
                                    String cpf,
                                    Planos planos,
                                    Double altura,
                                    Double peso,
                                    Especialidade objetivo) {
        return new DadosAlunos(
                nome,
                email,
                telefone,
                cpf,
                planos,
                altura,
                peso,
                objetivo);
    }

}
