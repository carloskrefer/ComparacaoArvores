package com.krefer;

import com.krefer.utils.impressoras.ImpressoraArvore;

public class ArvoreBinaria {
	private NoArvore raiz;
	
	public NoArvore getRaiz() {
		return raiz;
	}

	// Método que o usuário acessa para inserir um valor na árvore.
	public void inserir(int dado) {
		inserir(raiz, dado); // Argumento é o nó raiz pois sempre começamos analisando a raiz.
	}

	private void inserir(NoArvore no, int dado) {
		if (raiz == null) {
			raiz = new NoArvore(dado);
		} else if (dado >= no.getDado()) {
			if (no.getNoDireito() == null) {
				no.setNoDireito(new NoArvore(dado));
			} else {
				inserir(no.getNoDireito(), dado);
			}
		} else {
			if (no.getNoEsquerdo() == null) {
				no.setNoEsquerdo(new NoArvore(dado));
			} else {
				inserir(no.getNoEsquerdo(), dado);
			}
		}
	}

//	public void imprimir(NoArvore node, String prefix, boolean isLeft) {
//		if (node != null) {
//			System.out.println(prefix + (isLeft ? "├── " : "└── ") + node.getDado());
//
//			// Calcula os novos prefixos para os nós esquerdo e direito
//			String newPrefix = prefix + (isLeft ? "│ " : " ");
//
//			// Chama recursivamente para os filhos esquerdo e direito
//			imprimir(node.getNoDireito(), newPrefix, true);
//
//			imprimir(node.getNoEsquerdo(), newPrefix, false);
//		}
//
//		else {
//
//			System.out.println(prefix + (isLeft ? "├── " : "└── ") + "Vazio");
//		}
//	}
	
	public boolean buscar(int valor) {
		if (raiz == null) {
			return false;
		} else {
			return buscar(valor, raiz);
		}
	}

	// Não chamar quando raiz for nula
	private boolean buscar(int valorBuscado, NoArvore no) {
		if (no.getDado() == valorBuscado) {
			return true;
		} else if (valorBuscado >= no.getDado()) {
			if (no.getNoDireito() != null) {
				return buscar(valorBuscado, no.getNoDireito());
			} else {
				return false;
			}
		} else {
			if (no.getNoEsquerdo() != null) {
				return buscar(valorBuscado, no.getNoEsquerdo());
			} else {
				return false;
			}
		}
	}
	
	public void imprimir() {
		new ImpressoraArvore(this).imprimir();
	}
	
	public void remover(Integer valor) {
		remover(raiz, valor, null); // Começamos pela raiz. O pai é null pois
	} // a raiz não possui pai (por isso, null).

	private void remover(NoArvore noPercorrido, Integer valorRemover, NoArvore paiNoPercorrido) {
		// Este nó não é nulo (para evitar null pointer durante no.getDado)
		if (noPercorrido != null) {
			// Este nó não é nulo e é o nó que queremos remover.
			if (noPercorrido.getDado() == valorRemover) {
				// Este nó não é nulo, é aquele que queremos remover e não possui filhos.
				if (noPercorrido.getNoEsquerdo() == null && noPercorrido.getNoDireito() == null) {
					// Este nó não é nulo, é aquele que queremos remover, não possui filhos e é a
					// raiz.
					if (paiNoPercorrido == null) {
						raiz = null;
					// Este nó não é nulo, é aquele que queremos remover, não possui filhos, não é a
					// raiz e está a direita do pai.
					} else if (noPercorrido.getDado() >= paiNoPercorrido.getDado()) {
						paiNoPercorrido.setNoDireito(null);
					// Este nó não é nulo, é aquele que queremos remover, não possui filhos, não é a
					// raiz e está a esquerda do pai.
					} else {
						paiNoPercorrido.setNoEsquerdo(null);
					}
				// Este nó não é nulo, é aquele que queremos remover, só possui filho a direita.
				} else if (noPercorrido.getNoEsquerdo() == null) {
					// Este nó não é nulo, é aquele que queremos remover, só possui filho a direita,
					// e é a raiz.
					if (paiNoPercorrido == null) {
						raiz = noPercorrido.getNoDireito();
					// Este nó não é nulo, é aquele que queremos remover, só possui filho a direita
					// e está a direita do pai.
					} else if (noPercorrido.getDado() >= paiNoPercorrido.getDado()) {
						paiNoPercorrido.setNoDireito(noPercorrido.getNoDireito());
					// Este nó não é nulo, é aquele que queremos remover, só possui filho a direita
					// e está a esquerda do pai.
					} else {
						paiNoPercorrido.setNoEsquerdo(noPercorrido.getNoDireito());
					}
				// Este nó não é nulo, é aquele que queremos remover, só possui filho a
				// esquerda.
				} else if (noPercorrido.getNoDireito() == null) {
					// Este nó não é nulo, é aquele que queremos remover, só possui filho a
					// esquerda e é a raiz.
					if (paiNoPercorrido == null) {
						raiz = noPercorrido.getNoEsquerdo();
					// Este nó não é nulo, é aquele que queremos remover, só possui filho a esquerda
					// e está a direita do pai.
					} else if (noPercorrido.getDado() >= paiNoPercorrido.getDado()) {
						paiNoPercorrido.setNoDireito(noPercorrido.getNoEsquerdo());
					// Este nó não é nulo, é aquele que queremos remover, só possui filho a esquerda
					// e está a esquerda do pai.
					} else {
						paiNoPercorrido.setNoEsquerdo(noPercorrido.getNoEsquerdo());
					}
				// Este nó não é nulo, é aquele que queremos remover, possui filho a direita e a
				// esquerda.
				} else {
					// Obtém o  maior nó da esquerda do nó que queremos remover.
					NoArvore noMaiorEsquerda = buscarMaiorNoEsquerda(noPercorrido);
					
					remover(noPercorrido.getNoEsquerdo(), noMaiorEsquerda.getDado(), noPercorrido);
					
					// O nó do início que queríamos remover obtém o valor do maior nó a esquerda (ele que de fato foi removido).
					noPercorrido.setDado(noMaiorEsquerda.getDado());
				}
			// Valor que estou procurando remover não é igual ao valor do nó atual, mas é
			// maior.
			} else if (valorRemover > noPercorrido.getDado()) {
				remover(noPercorrido.getNoDireito(), valorRemover, noPercorrido);
			// Valor que estou procurando remover não é igual ao valor do nó atual, mas é
			// menor.
			} else {
				remover(noPercorrido.getNoEsquerdo(), valorRemover, noPercorrido);
			}
		}
	}

	private NoArvore buscarMaiorNoEsquerda(NoArvore no) {
		// Para encontrar o maior à esquerda do nó, primeiro temos que saber se existe
		// nós a esquerda.
		if (no.getNoEsquerdo() != null) {
			// O código abaixo busca o nó com o maior valor à esquerda do nó informado.
			no = no.getNoEsquerdo();
			while (no.getNoDireito() != null) {
				no = no.getNoDireito();
			}
		}
		return no;
	}
	
	public int buscarAltura() {
		return buscarAltura(raiz);
	}
	
	protected int buscarAltura(NoArvore no) {
		if (no == null) {
			return -1;
		}
		
		int esquerda = buscarAltura(no.getNoEsquerdo());
		int direita = buscarAltura(no.getNoDireito());
		
		if (esquerda > direita) {
			return 1 + esquerda;
		}
		
		return 1 + direita;
	}

}
