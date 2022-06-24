package com.crkw1.minhasFinancas.model.repository;

import com.crkw1.minhasFinancas.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
