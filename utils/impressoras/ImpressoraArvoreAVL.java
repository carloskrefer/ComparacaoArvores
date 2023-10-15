package com.krefer.utils.impressoras;

import com.krefer.utils.lista_encadeada.Lista;
import com.krefer.utils.lista_encadeada.No;
import com.krefer.ArvoreBinariaAVL;
import com.krefer.NoArvoreAVL;

public class ImpressoraArvoreAVL {
	private Lista<Lista<NoArvoreAVL>> listaSuperior;
	private ArvoreBinariaAVL arvore;
	
	public ImpressoraArvoreAVL(ArvoreBinariaAVL arvore) {
		this.arvore = arvore;
		listaSuperior = new Lista<Lista<NoArvoreAVL>>();
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
		No<Lista<NoArvoreAVL>> noSuperior = listaSuperior.getPrimeiroNo();
		Lista<NoArvoreAVL> listaInterna;
		No<NoArvoreAVL> noInterno;
		boolean isDadoInexistente = true;
		
		do {
			//if pra nao imprimir a última lista que só contém -1
			if (noSuperior.getProximoNo() == null) {
				break;
			}
			
			listaInterna = noSuperior.getDado();
			noInterno = listaInterna.getPrimeiroNo();	
			
			do {
				isDadoInexistente = noInterno.getDado().getDado() == -1;
				System.out.print((
						isDadoInexistente ? "-" : (noInterno.getDado().getDado())
								// + "(" + noInterno.getDado().getFatorBalanceamento() + ")"
								) + 
						"\t");
				noInterno = noInterno.getProximoNo();
			} while (noInterno != null);
			System.out.println();
			noSuperior = noSuperior.getProximoNo();
		} while (noSuperior != null);
	}
	
	private void inserirRaizNaListaSuperior() {
		Lista<NoArvoreAVL> listaAninhada = new Lista<NoArvoreAVL>();
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
		No<NoArvoreAVL> noAninhadoAux;
		
		//((X -> null) -> (null) -> null)	
		//                ^ Adicionada nova lista dentro da listaSuperior pro noSuperior poder usá-lo
		listaSuperior.inserir(new Lista<NoArvoreAVL>());

		No<Lista<NoArvoreAVL>> noSuperior = listaSuperior.getPrimeiroNo();
		Lista<NoArvoreAVL> listaAninhadaAux;
		// Este do-while serve para percorrer a lista superior
		do {
			noAninhadoAux = noSuperior.getDado().getPrimeiroNo();
			isListaAninhadaAuxPopuladaApenasComFlags = true;			
			noSuperior = noSuperior.getProximoNo();
			listaAninhadaAux = noSuperior.getDado();
			
			// Este do-while serve para percorrer a lista interna
			do {
				if (noAninhadoAux.getDado().getDado() == FLAG_NO_NAO_EXISTE) {
					listaAninhadaAux.inserir(new NoArvoreAVL(FLAG_NO_NAO_EXISTE));
					listaAninhadaAux.inserir(new NoArvoreAVL(FLAG_NO_NAO_EXISTE));
					noAninhadoAux = noAninhadoAux.getProximoNo();
					continue;
				}
				
				if (noAninhadoAux.getDado().getNoEsquerdo() != null) {
					listaAninhadaAux.inserir(noAninhadoAux.getDado().getNoEsquerdo());
					isListaAninhadaAuxPopuladaApenasComFlags = false;
				} else {
					listaAninhadaAux.inserir(new NoArvoreAVL(FLAG_NO_NAO_EXISTE));
				}
				
				if (noAninhadoAux.getDado().getNoDireito() != null) {
					listaAninhadaAux.inserir(noAninhadoAux.getDado().getNoDireito());
					isListaAninhadaAuxPopuladaApenasComFlags = false;
				} else {
					listaAninhadaAux.inserir(new NoArvoreAVL(FLAG_NO_NAO_EXISTE));
				}
				
				noAninhadoAux = noAninhadoAux.getProximoNo();
			} while ((noAninhadoAux != null));
			
			if (!isListaAninhadaAuxPopuladaApenasComFlags) {
				listaSuperior.inserir(new Lista<NoArvoreAVL>());
			}	
		} while (!isListaAninhadaAuxPopuladaApenasComFlags);
	}
	
}
