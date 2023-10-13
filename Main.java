package com.krefer;

public class Main {

	public static void main(String[] args) {
		ArvoreBinariaAVL a = new ArvoreBinariaAVL();
		
		a.inserir(20);
		a.inserir(15);
		a.inserir(25);
		a.inserir(10);
		a.inserir(17);
		a.inserir(30);
		a.inserir(5);
		a.inserir(12);
		a.inserir(18);
		a.inserir(16);
		a.inserir(19);

		a.imprimir();
	}

}
