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
		
		popularListaSuperior();
		
		imprimirListaSuperior();
	}
	
	private void imprimirListaSuperior() {
		No<Lista<NoArvore>> noSuperior = listaSuperior.getPrimeiroNo();
		Lista<NoArvore> listaInterna;
		No<NoArvore> noInterno;
		
		do {
			listaInterna = noSuperior.getDado();
			noInterno = listaInterna.getPrimeiroNo();	
			
//			do {
//				System.out.println("");
//			} while (noInterno.getDado() != null);
			
			listaInterna.imprimir();
			
			noSuperior = noSuperior.getProximoNo();
		} while (noSuperior != null);
	}
	
	private void inserirRaizNaListaSuperior() {
		Lista<NoArvore> listaAninhada = new Lista<NoArvore>();
		listaAninhada.inserir(arvore.getRaiz()); 
		listaSuperior.inserir(listaAninhada);				
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
		int FLAG_NO_NAO_EXISTE = -1;
		boolean isListaAninhadaAuxPopuladaApenasComFlags;
		
		//((X -> null) -> null)
		//  ^ noAninhadoAux
		No<NoArvore> noAninhadoAux = listaSuperior.getPrimeiroNo().getDado().getPrimeiroNo();
		
		//((X -> null) -> (null) -> null)	
		//                ^ Adicionada nova lista dentro da listaSuperior pro noSuperior poder usá-lo
		listaSuperior.inserir(new Lista<NoArvore>());

		No<Lista<NoArvore>> noSuperior = listaSuperior.getPrimeiroNo();
		Lista<NoArvore> listaAninhadaAux;
		// Este do-while serve para percorrer a lista superior
		do {

			isListaAninhadaAuxPopuladaApenasComFlags = true;			
			noSuperior = noSuperior.getProximoNo();
			listaAninhadaAux = noSuperior.getDado();
			
			// Este do-while serve para percorrer a lista interna
			do {
				if (noAninhadoAux.getDado().getDado() == FLAG_NO_NAO_EXISTE) {
					listaAninhadaAux.inserir(new NoArvore(FLAG_NO_NAO_EXISTE));
					listaAninhadaAux.inserir(new NoArvore(FLAG_NO_NAO_EXISTE));
					continue;
				}
				
				if (noAninhadoAux.getDado().getNoEsquerdo() != null) {
					listaAninhadaAux.inserir(noAninhadoAux.getDado().getNoEsquerdo());
					isListaAninhadaAuxPopuladaApenasComFlags = false;
				} else {
					listaAninhadaAux.inserir(new NoArvore(FLAG_NO_NAO_EXISTE));
				}
				
				if (noAninhadoAux.getDado().getNoDireito() != null) {
					listaAninhadaAux.inserir(noAninhadoAux.getDado().getNoDireito());
					isListaAninhadaAuxPopuladaApenasComFlags = false;
				} else {
					listaAninhadaAux.inserir(new NoArvore(FLAG_NO_NAO_EXISTE));
				}
				
				noAninhadoAux = noAninhadoAux.getProximoNo();
			} while ((noAninhadoAux != null));
			
			if (!isListaAninhadaAuxPopuladaApenasComFlags) {
				listaSuperior.inserir(new Lista<NoArvore>());
			}	
		} while (!isListaAninhadaAuxPopuladaApenasComFlags);
	}
	
}
