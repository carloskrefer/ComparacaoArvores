package com.krefer;

public class Main {

	public static void main(String[] args) {
		ArvoreBinaria a = new ArvoreBinaria();
		
		a.inserir(5);
		a.inserir(4);
		a.inserir(6);
		a.inserir(7);
		a.inserir(10);
		a.inserir(12);
		a.inserir(9);
		
		a.imprimir();
	}

}
