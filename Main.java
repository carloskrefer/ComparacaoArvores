package com.krefer;

public class Main {

	public static void main(String[] args) {
		ArvoreBinariaAVL a = new ArvoreBinariaAVL();
		
		a.inserir(15);
		a.inserir(6);
		a.inserir(9);
		a.inserir(12);
		a.inserir(8);
		a.inserir(14);
		
		a.inserir(20);
		a.inserir(18);
		a.inserir(22);
		a.imprimir();
		
		a.remover(9);
		
		a.imprimir();
	}

}
