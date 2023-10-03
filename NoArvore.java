package com.krefer;

public class NoArvore {
	private Integer dado;
	private NoArvore noEsquerdo;
	private NoArvore noDireito;
	
	public NoArvore(int dado) {
		this.dado = dado;
	}
	
	public Integer getDado() {
		return dado;
	}
	
	public void setDado(Integer dado) {
		this.dado = dado;
	}
	
	public NoArvore getNoEsquerdo() {
		return noEsquerdo;
	}
	
	public void setNoEsquerdo(NoArvore noEsquerdo) {
		this.noEsquerdo = (NoArvore) noEsquerdo;
	}
	
	public NoArvore getNoDireito() {
		return noDireito;
	}
	
	public void setNoDireito(NoArvore noDireito) {
		this.noDireito = (NoArvore) noDireito;
	}
}
