package com.monstergym.api.repository;

import com.monstergym.api.model.agendamento.Agendamento;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    boolean existsByIdAndData(@NotNull Long treinadorId, @NotNull LocalDateTime data);
}
