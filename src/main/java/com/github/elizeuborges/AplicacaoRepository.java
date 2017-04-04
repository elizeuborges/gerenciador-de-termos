package com.github.elizeuborges;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AplicacaoRepository extends JpaRepository<Aplicacao, Long> {

	Optional<Aplicacao> findByNome(String nome);

}
