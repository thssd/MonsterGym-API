package com.monstergym.api.domain.pagamentos;

import com.monstergym.api.domain.alunos.Aluno;
import com.monstergym.api.domain.alunos.Planos;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(of = "id")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pagamentos")
@Entity
public class Pagamento {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Double valor;
    private LocalDateTime dataHora;
    @Enumerated(EnumType.STRING)
    private Planos plano;
    @OneToOne
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;


    public Long idAluno() {
        return aluno.getId();
    }
}
