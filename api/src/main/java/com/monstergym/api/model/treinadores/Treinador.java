package com.monstergym.api.model.treinadores;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Entity
@Table(name = "treinadores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Treinador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cref;
    private String telefone;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    private Boolean ativo;

    public Treinador(DadosTreinadores dadosTreinadores) {
        this.ativo = true;
        this.nome = dadosTreinadores.nome();
        this.cref = dadosTreinadores.cref();
        this.telefone = dadosTreinadores.telefone();
        this.especialidade = dadosTreinadores.especialidade();
    }

    public void atualizarInformacoes(@Valid DadosAtualizarTreinador dadosAtualizarTreinador){
        if (dadosAtualizarTreinador.nome() != null) {
            this.nome = dadosAtualizarTreinador.nome();
        }
        if (dadosAtualizarTreinador.telefone() != null) {
            this.telefone = dadosAtualizarTreinador.telefone();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
