package com.monstergym.api.domain.alunos;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private Boolean ativo;
    @Enumerated(EnumType.STRING)
    private Planos plano;

    public Aluno(DadosAlunos dadosAlunos) {
        this.ativo = true;
        this.nome = dadosAlunos.nome();
        this.email = dadosAlunos.email();
        this.telefone = dadosAlunos.telefone();
        this.cpf = dadosAlunos.cpf();
        this.plano = dadosAlunos.plano();
    }

    public void atualizarInformacoes(@Valid DadosAtualizarAluno dadosAtualizarAluno) {
        if (dadosAtualizarAluno.nome() != null) {
            this.nome = dadosAtualizarAluno.nome();
        }
        if (dadosAtualizarAluno.email() != null) {
            this.email = dadosAtualizarAluno.email();
        }
        if (dadosAtualizarAluno.telefone() != null) {
            this.telefone = dadosAtualizarAluno.telefone();
        }
        if (dadosAtualizarAluno.plano() != null) {
            this.plano = dadosAtualizarAluno.plano();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
