package com.monstergym.api.repository;

import com.monstergym.api.domain.treinadores.Especialidade;
import com.monstergym.api.domain.treinadores.Treinador;
import jakarta.validation.constraints.Future;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TreinadorRepository extends JpaRepository<Treinador, Long>{

    Page<Treinador> findAllByAtivoTrue(Pageable pageable);

    Treinador escolherTreinadorAleatorio(Especialidade especialidade, @Future LocalDateTime data);
}
