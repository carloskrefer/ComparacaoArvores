package com.krefer;

import com.krefer.interfaces.NoArvore;

public class NoArvoreImpl implements NoArvore {
	private Integer dado;
	private NoArvoreImpl noEsquerdo;
	private NoArvoreImpl noDireito;
	
	public NoArvoreImpl(int dado) {
		this.dado = dado;
	}
	
	public Integer getDado() {
		return dado;
	}
	
	public void setDado(Integer dado) {
		this.dado = dado;
	}
	
	public NoArvoreImpl getNoEsquerdo() {
		return noEsquerdo;
	}
	
	public void setNoEsquerdo(NoArvore noEsquerdo) {
		this.noEsquerdo = (NoArvoreImpl) noEsquerdo;
	}
	
	public NoArvoreImpl getNoDireito() {
		return noDireito;
	}
	
	public void setNoDireito(NoArvore noDireito) {
		this.noDireito = (NoArvoreImpl) noDireito;
	}
}
