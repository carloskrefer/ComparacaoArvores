package com.krefer;

public class Main {

	public static void main(String[] args) {
		ArvoreBinariaAVL a = new ArvoreBinariaAVL();
		
		a.inserir(20);
		a.inserir(10);
		a.inserir(30);
		a.inserir(8);
		a.inserir(12);
		a.inserir(25);
		a.inserir(35);
		a.inserir(11);
		a.inserir(24);
		a.inserir(27);
		a.inserir(32);
		a.inserir(36);
		a.inserir(26);
		a.inserir(28);
		
		
		
		a.imprimir();
		
		System.out.println();
		System.out.println();
		
		a.remover(20);
		
		a.imprimir();
//		
//		a.inserir(5);
//		a.imprimir();
	}

}
