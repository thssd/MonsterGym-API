package com.monstergym.api.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.monstergym.api.model.treinadores.Treinador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreinadorRepository extends JpaRepository<Treinador, Long>{
    Page<Treinador> findAllByAtivoTrue(Pageable pageable);
}
