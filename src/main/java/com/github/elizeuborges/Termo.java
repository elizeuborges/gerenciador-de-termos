package com.github.elizeuborges;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Termo {

	@Id
    @GeneratedValue
    private Long id;
	
	@JsonIgnore
    @ManyToOne
	private Aplicacao aplicacao;
	
	@OneToOne
	@JoinColumn
	private Arquivo arquivo;
	
	private String descricao;
	
	Termo() { // somente para JPA
	}

	public Termo(Aplicacao aplicacao, String descricao, Arquivo arquivo) {
		this.aplicacao = aplicacao;
		this.descricao = descricao;
		this.arquivo = arquivo;
	}

	public Long getId() {
		return id;
	}
	
	public String getDescricao() {
		return descricao;
	}
		
	public Aplicacao getAplicacao() {
		return aplicacao;
	}
	
	public Arquivo getArquivo() {
		return arquivo;
	}
	
}
