package com.monstergym.api.repository;

import com.monstergym.api.domain.aulas.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AulaRepository extends JpaRepository<Aula, Long> {
}
