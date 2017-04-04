package com.github.elizeuborges;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Aplicacao {

	@Id
    @GeneratedValue
    private Long id;
	
	@OneToMany(mappedBy = "aplicacao")
    private Set<Termo> termos = new HashSet<>();

	private String nome;
	
	Aplicacao() {
		// somente para JPA
	}
	
	public Aplicacao(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	public Long getId() {
		return id;
	}
	
	public Set<Termo> getTermos() {
		return termos;
	}
	
}
