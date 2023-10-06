package com.krefer.utils.pilha_dinamica;

public class Pilha<T> {
	private No<T> topo;
	private int tamanho;
	
	public int getTamanho() {
		return tamanho;
	}
	
	public No<T> getTopo() {
		return topo;
	}
	
	public Pilha() {
		topo = null;
		tamanho = 0;
	}
	
	public boolean estaVazia() {
		return (topo == null);
	}
	
	public void inserir(T dado) {
		No<T> antigoTopo = topo;
		topo = new No<T>(dado);
		topo.setProximoNo(antigoTopo);
		tamanho++;
	}
	
	public void inserir(No<T> no) {
		No<T> antigoTopo = topo;
		topo = no;
		topo.setProximoNo(antigoTopo);
		tamanho++;
	}
	
	public No<T> remover() {
		if (estaVazia()) {
			return null;
		} else {
			tamanho--;
			No<T> antigoTopo = topo;
			topo = topo.getProximoNo();
			return antigoTopo;
		}
	}
	
	public void imprimir() {
		No<T> no = topo;
		while (no != null) {
			System.out.print(no.getDado() + " -> ");
			no = no.getProximoNo();
		}
		System.out.println("null");
	}
	
}
