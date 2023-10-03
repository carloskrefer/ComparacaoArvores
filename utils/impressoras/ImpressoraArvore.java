package com.krefer.utils.impressoras;

import com.krefer.interfaces.ArvoreBinaria;
import com.krefer.interfaces.NoArvore;
import com.krefer.utils.lista_encadeada.Lista;
import com.krefer.utils.lista_encadeada.No;

public class ImpressoraArvore {
	private Lista<Lista<Integer>> listaSuperior;
	private ArvoreBinaria arvore;
	
	public ImpressoraArvore(ArvoreBinaria arvore) {
		this.arvore = arvore;
		listaSuperior = new Lista<Lista<Integer>>();
	}
	
	public void imprimir() {
		if (arvore.getRaiz() == null) {
			System.out.println("Árvore vazia.");
			return;
		}
		
		inserirRaizNaListaSuperior();
		
		inserirNoProximoElementoDaListaSuperiorOsFilhosDosNosDoElementoAnterior();
		
		
	}
	
	private void inserirRaizNaListaSuperior() {
		Lista<Integer> listaAninhada = new Lista<Integer>();
		// Abaixo fiz new No<Integer>() ao invés de apenas arvore.getRaiz() porque
		// arvore é do tipo ArvoreBinaria que não é genérico, e inserir() de NoArvore 
		// espera um valor genérico.
		// Não a interface ArvoreBinaria ser genérico porque a implementação dela
		// só deverá aceitar inteiros (pois ele compara se um nó é maior ou igual ao
		// outro nó, algo que não é possível se forem objetos). Nesse caso eu não
		// ganharia nada em fazer ela ser T extends Integer ou algo do tipo.
		listaAninhada.inserir(new No<Integer>(arvore.getRaiz().getDado())); 
		listaSuperior.inserir(listaAninhada);				
	}
	
	private void inserirNoProximoElementoDaListaSuperiorOsFilhosDosNosDoElementoAnterior() {
		popularListaSuperior();
	}
	
	// Sendo feK o filho esquerdo de K, fdK o filho direito de K, () simbolizando uma 
	// lista de nós e X um nó raiz, este método fará o seguinte:
	//
	// Estado inicial (a ser fornecido no atributo listaSuperior antes de chamar este método):
	// 		((X -> null) -> null)	
	//
	// 1ª Iteração:
	// 		((X -> null) -> (feX -> fdX -> null) -> null)
	//        ^ É o noAninhadoAux
	//                      ^ É a listaAninhadaAux
	// 2ª Iteração:
	// 		((X -> null) -> (feX -> fdX -> null) -> (feFEX -> fdFEX -> feFDX -> fdFDX -> null)) -> null)
	//						  ^ É o noAninhadoAux   ^ É a listaAninhadaAux
	// etc.
	private void popularListaSuperior() {
		No<Integer> noAninhadoAux = listaSuperior.getPrimeiroNo().getDado().getPrimeiroNo();
		Lista<Integer> listaAninhadaAux = listaSuperior.getPrimeiroNo().getProximoNo().getDado();
		
		while (noAninhadoAux != null) {
			if (noAninhadoAux.getDado().get)
			listaAux.inserir(noAninhadoAux);
			noAninhadoAux.getProximoNo();
		}
		
		
	}
	
	
	
	
	

	

}
