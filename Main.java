package com.krefer;

public class Main {

	public static void main(String[] args) {
		ArvoreBinariaAVL a = new ArvoreBinariaAVL();
		
		a.inserir(10);
		a.inserir(5);
		a.inserir(13);
		a.inserir(6);
		a.inserir(4);
		a.inserir(2);

		a.imprimir();
	}

}
