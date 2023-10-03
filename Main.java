package com.krefer;

public class Main {

	public static void main(String[] args) {
		ArvoreBinaria a = new ArvoreBinaria();
		
		a.inserir(5);
		a.inserir(6);
		a.inserir(4);
		
		System.out.println(a.buscarAltura());
		
		a.imprimir();
		
		System.out.println("oi");
	}

}
