package com.krefer;

public class Main {

	public static void main(String[] args) {
		ArvoreBinariaAVL a = new ArvoreBinariaAVL();
		
		a.inserir(10);
		a.inserir(8);
		a.inserir(15);
		a.inserir(12);
		a.inserir(18);
		a.inserir(20);
		// deu certo, só falta calcular o fator de balanceamento de todo mundo depois da rotação.
		// pois eu só recalculo o fator de balanceamento até chegar no -2
		
		
		// ver se dá pra RECALCULAR O BALANCEAMENTO no SET do NÓ.
		// obs: não dá. porque pode ser q eu mude um filho... e isso pode alterar o balanceamento dum avô.
		// 
		a.imprimir();
	}

}
