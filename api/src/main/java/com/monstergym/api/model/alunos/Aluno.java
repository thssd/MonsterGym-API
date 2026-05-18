package com.monstergym.api.model.alunos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public Aluno(DadosAlunos dadosAlunos) {
        this.ativo = true;
        this.nome = dadosAlunos.nome();
        this.email = dadosAlunos.email();
        this.telefone = dadosAlunos.telefone();
        this.cpf = dadosAlunos.cpf();
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
    }

    public void excluir() {
        this.ativo = false;
    }
}
