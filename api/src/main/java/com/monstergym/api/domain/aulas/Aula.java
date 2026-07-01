package com.monstergym.api.domain.aulas;

import com.monstergym.api.domain.alunos.Aluno;
import com.monstergym.api.domain.aulas.cancelamentos.MotivoCancelamento;
import com.monstergym.api.domain.treinadores.Especialidade;
import com.monstergym.api.domain.treinadores.Treinador;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "consultas")
@Entity
public class Aula {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treinador_id")
    private Treinador treinador;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
    private LocalDateTime data;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Enumerated(EnumType.STRING)
    private MotivoCancelamento motivoCancelamento;
    private String descricao;

    public Aula(Aula aula) {
        this.id = aula.id;
        this.treinador = aula.treinador;
        this.aluno = aula.aluno;
        this.data = aula.data;
    }

    public Aula(Long id, Treinador treinador, Aluno aluno, LocalDateTime data) {
        this.id = id;
        this.treinador = treinador;
        this.aluno = aluno;
        this.data = data;
    }

    public void cancelar (@NotNull MotivoCancelamento motivoCancelamento, String descricao){
        this.motivoCancelamento = motivoCancelamento;
        this.descricao = descricao;
    }
}
