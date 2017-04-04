package com.github.elizeuborges;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{appId}/termo")
public class TermosController {

	@Autowired
	private TermoRepository termoRepository;

	@Autowired
	private AplicacaoRepository aplicacaoRepository;
	
	@GetMapping
	public Collection<Termo> verTermos(@PathVariable String appId) {
		this.validarAplicacao(appId);
		return this.termoRepository.findByAplicacaoNome(appId);
	}
	
	private void validarAplicacao(String nome) {
		this.aplicacaoRepository.findByNome(nome)
			.orElseThrow(() -> new AplicacaoNaoEncontradaException(nome));
	}
	
}
