package com.github.elizeuborges;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TermoRepository extends JpaRepository<Termo, Long> {

	Collection<Termo> findByAplicacaoNome(String appId);
	
}
