package com.monstergym.api.model.agendamento;

import com.monstergym.api.model.alunos.Aluno;
import com.monstergym.api.model.treinadores.Treinador;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime data;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
    @ManyToOne
    @JoinColumn(name = "treinador_id")
    private Treinador treinador;

    public Agendamento(Long id, Treinador treinador, Aluno aluno, @NotNull LocalDateTime data, Status status) {
        this.id = id;
        this.treinador = treinador;
        this.aluno = aluno;
        this.data = data;
        this.status = status;
    }
}
