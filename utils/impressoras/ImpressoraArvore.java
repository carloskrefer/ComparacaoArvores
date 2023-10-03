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
		listaAninhada.inserir(arvore.getRaiz().getDado()); // aqui está o ERRO! É PRA INSERIR O NOARVORE, E NÃO SÓ O VALOR DELE!
		listaSuperior.inserir(listaAninhada);				// POIS NO MÉTODO POPULARLISTASUPERIOR VAMOS PERCORRER ESQUERDA E DIREITA DELE!
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
	//                      ^ É a listaAux
	// 2ª Iteração:
	// 		((X -> null) -> (feX -> fdX -> null) -> (feFEX -> fdFEX -> feFDX -> fdFDX -> null)) -> null)
	//						  ^ É o noAninhadoAux   ^ É a listaAux
	// etc.
	private void popularListaSuperior() {
		No<Integer> noAninhadoAux = listaSuperior.getPrimeiroNo().getDado().getPrimeiroNo();
		Lista<Integer> listaAux = listaSuperior.getPrimeiroNo().getProximoNo().getDado();
		
		while (noAninhadoAux != null) {
			// agora, se o filho esquerdo de noAninhadoAux for nulo, insere -1. senão, insere o valor dele.
			// agora, se o filho direito  de noAninhadoAux for nulo, insere -1. senão, insere o valor dele.
			
			listaAux.inserir(noAninhadoAux);
			noAninhadoAux.getProximoNo();
		}
		
		
	}
	
	
	
	
	

	

}
