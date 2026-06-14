package com.monstergym.api.repository;

import com.monstergym.api.domain.treinadores.Especialidade;
import com.monstergym.api.domain.treinadores.Treinador;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TreinadorRepository extends JpaRepository<Treinador, Long>{

    Page<Treinador> findAllByAtivoTrue(Pageable pageable);

    @Query("select t from Treinador t where t.ativo = true and t.especialidade = :especialidade and t.id not in(" +
            "select a.treinador.id from Aula a where a.data = :data)order by rand() limit 1")
    Treinador escolherTreinadorAleatorio(Especialidade especialidade, @Future  @NotNull LocalDateTime data);

    @Query("select t.ativo from Treinador t where t.id = :idTreinador")
    Boolean findAtivoById(@NotNull Long idTreinador);
}
