package com.krefer;

public class NoArvoreAVL {
	private Integer dado;
	private NoArvoreAVL noEsquerdo;
	private NoArvoreAVL noDireito;
	private int fatorBalanceamento;
	
	public int getFatorBalanceamento() {
		return fatorBalanceamento;
	}

	public NoArvoreAVL(int dado) {
		this.dado = dado;
	}
	
	public Integer getDado() {
		return dado;
	}
	
	public void setDado(Integer dado) {
		this.dado = dado;
	}
	
	public NoArvoreAVL getNoEsquerdo() {
		return noEsquerdo;
	}
	
	public void setNoEsquerdo(NoArvoreAVL noEsquerdo) {
		this.noEsquerdo = (NoArvoreAVL) noEsquerdo;
	}
	
	public NoArvoreAVL getNoDireito() {
		return noDireito;
	}
	
	public void setNoDireito(NoArvoreAVL noDireito) {
		this.noDireito = (NoArvoreAVL) noDireito;
	}
	
	public void atualizarFatorBalanceamento() {
		fatorBalanceamento = 
			   (ArvoreBinariaAVL.buscarAltura(noEsquerdo) - 
				ArvoreBinariaAVL.buscarAltura(noDireito));
	}
}
