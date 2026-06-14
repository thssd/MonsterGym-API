package com.monstergym.api.repository;

import com.monstergym.api.domain.aulas.Aula;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AulaRepository extends JpaRepository<Aula, Long> {

    Boolean existsByTreinadorIdAndData(Long aLong, @Future LocalDateTime data);

    Boolean existsByAlunoIdAndDataBetween(@NotNull Long aLong, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
}
