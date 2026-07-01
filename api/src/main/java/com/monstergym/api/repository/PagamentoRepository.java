package com.monstergym.api.repository;

import com.monstergym.api.domain.pagamentos.Pagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Page<Pagamento> findAll(Pageable pageable);

    Optional<Pagamento> findTopByAlunoIdOrderByDataHoraDesc(Long id);
}
