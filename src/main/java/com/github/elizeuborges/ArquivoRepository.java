package com.github.elizeuborges;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

	Collection<Arquivo> findByAplicacaoNome(String appId);

}
