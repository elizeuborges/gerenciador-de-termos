package com.github.elizeuborges;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Arquivo {

	@Id
    @GeneratedValue
    private Long id;
	
	private String nome;
	
	@JsonIgnore
	private byte[] data;
	
	@OneToOne
	@JoinColumn
	private Aplicacao aplicacao;
	
	Arquivo() {
		// somente para JPA
	}

	public Arquivo(Aplicacao aplicacao, String nome, byte[] data) {
		this.aplicacao = aplicacao;
		this.nome = nome;
		this.data = data;
	}

	public Aplicacao getAplicacao() {
		return aplicacao;
	}
	
	public String getNome() {
		return nome;
	}
	
	public Long getId() {
		return id;
	}
	
	public byte[] getData() {
		return data;
	}
	
}
