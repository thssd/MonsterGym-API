package com.monstergym.api.repository;

import com.monstergym.api.domain.alunos.Aluno;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Page<Aluno> findAllByAtivoTrue(Pageable pageable);

    @Query("select a.ativo from Aluno a where a.id = :id")
    Boolean findAtivoById(@NotNull Long id);
}
