package com.trilha.back.finacys.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trilha.back.finacys.entity.Lancamento;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{
	
	@Query(value = "select l from Lancamento l where paid = ?1")
	public List<Lancamento> listarLancamentosPagos(Optional<Boolean> paid);
	
}


