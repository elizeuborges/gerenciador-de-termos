package com.github.elizeuborges;

public class AplicacaoNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = -907115240855434238L;

	public AplicacaoNaoEncontradaException(String appId) {
		super("não foi encontrada a aplicacao '"+appId+"'.");
	}
	
}
