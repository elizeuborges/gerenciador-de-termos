package com.github.elizeuborges;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TermsAcceptanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TermsAcceptanceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(AplicacaoRepository aplicacaoRepository, TermoRepository termoRepository) {
		return (evt) -> Arrays.asList(
				"basf,dhl,danone,whilpool".split(","))
				.forEach(
						nomeDaAplicacao -> {
							Aplicacao aplicacao = aplicacaoRepository.save(new Aplicacao(nomeDaAplicacao));
							termoRepository.save(new Termo(aplicacao, "Primeiro Termo da aplicacao '"+nomeDaAplicacao+"'", null));
							termoRepository.save(new Termo(aplicacao, "Segundo Termo da aplicacao '"+nomeDaAplicacao+"'", null));
						});
	}
	
}
