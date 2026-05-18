package com.monstergym.api.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.monstergym.api.model.alunos.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Page<Aluno> findAllByAtivoTrue(Pageable pageable);
}
