package com.krefer.utils.impressoras;

import com.krefer.utils.lista_encadeada.Lista;
import com.krefer.utils.lista_encadeada.No;
import com.krefer.ArvoreBinaria;
import com.krefer.NoArvore;

public class ImpressoraArvore {
	private Lista<Lista<NoArvore>> listaSuperior;
	private ArvoreBinaria arvore;
	
	public ImpressoraArvore(ArvoreBinaria arvore) {
		this.arvore = arvore;
		listaSuperior = new Lista<Lista<NoArvore>>();
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
		Lista<NoArvore> listaAninhada = new Lista<NoArvore>();
		listaAninhada.inserir(arvore.getRaiz()); 
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
		//((X -> null) -> null)
		//  ^ noAninhadoAux
		No<NoArvore> noAninhadoAux = listaSuperior.getPrimeiroNo().getDado().getPrimeiroNo();
		//((X -> null) -> (null) -> null)	
		//                ^ Adicionada nova lista dentro da listaSuperior
		listaSuperior.inserir(new Lista<NoArvore>());
		//((X -> null) -> (null) -> null)	
		//               
		
		Lista<NoArvore> listaAninhadaAux = listaSuperior.getPrimeiroNo().getProximoNo().getDado();
		Integer FLAG_NO_NAO_EXISTE = -1;
		
			
		do {
			if (noAninhadoAux.getDado().getDado() == FLAG_NO_NAO_EXISTE) {
				listaAninhadaAux.inserir(new NoArvore(FLAG_NO_NAO_EXISTE));
				listaAninhadaAux.inserir(new NoArvore(FLAG_NO_NAO_EXISTE));
				continue;
			}
			
			if (noAninhadoAux.getDado().getNoEsquerdo() != null) {
				listaAninhadaAux.inserir(noAninhadoAux.getDado().getNoEsquerdo());
			} else {
				listaAninhadaAux.inserir(new NoArvore(FLAG_NO_NAO_EXISTE));
			}
			
			if (noAninhadoAux.getDado().getNoDireito() != null) {
				listaAninhadaAux.inserir(noAninhadoAux.getDado().getNoDireito());
			} else {
				listaAninhadaAux.inserir(new NoArvore(FLAG_NO_NAO_EXISTE));
			}
			
			noAninhadoAux.getProximoNo();
		} while (noAninhadoAux != null);
		
		
	}
	
	
	
	
	

	

}
