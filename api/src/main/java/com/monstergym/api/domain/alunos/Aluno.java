package com.monstergym.api.domain.alunos;

import com.monstergym.api.domain.treinadores.Especialidade;
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

    private Double altura;
    private Double peso;
    private Especialidade objetivo;
    private Sexo sexo;


    public Aluno(DadosAlunos dadosAlunos) {
        this.ativo = true;
        this.nome = dadosAlunos.nome();
        this.email = dadosAlunos.email();
        this.telefone = dadosAlunos.telefone();
        this.cpf = dadosAlunos.cpf();
        this.plano = dadosAlunos.plano();
        this.altura = dadosAlunos.altura();
        this.peso = dadosAlunos.peso();
        this.objetivo = dadosAlunos.objetvo();
    }

    public void atualizarInformacoes(@Valid DadosAtualizarAluno dadosAtualizarAluno){
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

    public void realizarAvaliacao(DadosAvaliacaoAlunos dados) {
        if (dados.altura() != null) {
            this.altura = dados.altura();
        }
        if (dados.peso() != null) {
            this.peso = dados.peso();
        }
        if (dados.objetivo() != null) {
            this.objetivo = dados.objetivo();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
