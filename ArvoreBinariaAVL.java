package com.krefer;

import com.krefer.utils.impressoras.ImpressoraArvoreAVL;

public class ArvoreBinariaAVL {
	private NoArvoreAVL raiz;

	public NoArvoreAVL getRaiz() {
		return raiz;
	}

	// Método que o usuário acessa para inserir um valor na árvore.
	public void inserir(int dado) {
		inserir(raiz, dado); 
	}

	private void inserir(NoArvoreAVL no, int dado) {
		if (raiz == null) {
			raiz = new NoArvoreAVL(dado);
		} else if (dado >= no.getDado()) {
			if (no.getNoDireito() == null) {
				no.setNoDireito(new NoArvoreAVL(dado));
			} else {
				inserir(no.getNoDireito(), dado);
			}
		} else {
			if (no.getNoEsquerdo() == null) {
				no.setNoEsquerdo(new NoArvoreAVL(dado));
			} else {
				inserir(no.getNoEsquerdo(), dado);
			}
		}
	}

//	public void imprimir(NoArvoreAVL node, String prefix, boolean isLeft) {
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

	public void buscar(int valor) {
		buscar(valor, raiz);
	}

	// Não chamar quando raiz for nula
	private void buscar(int valorBuscado, NoArvoreAVL no) {
		if (no.getDado() == valorBuscado) {
			System.out.println("Valor " + valorBuscado + " encontrado!");
		} else if (valorBuscado >= no.getDado()) {
			if (no.getNoDireito() != null) {
				buscar(valorBuscado, no.getNoDireito());
			} else {
				System.out.println("Valor " + valorBuscado + " não encontrado...");
			}
		} else {
			if (no.getNoEsquerdo() != null) {
				buscar(valorBuscado, no.getNoEsquerdo());
			} else {
				System.out.println("Valor " + valorBuscado + " não encontrado...");
			}
		}
	}

	public void imprimir() {
		new ImpressoraArvoreAVL(this).imprimir();
	}

	public void remover(Integer valor) {
		removerNo(raiz, valor, null); // Começamos pela raiz. O pai é null pois
	} // a raiz não possui pai (por isso, null).

	private NoArvoreAVL removerNo(NoArvoreAVL no, Integer valor, NoArvoreAVL pai) {
		// Este nó não é nulo. Isso é importante, pois daria erro ao fazer o próximo IF
		// comparando um null com um valor.
		if (no != null) {
			// Este nó não é nulo e é o nó que queremos remover.
			if (no.getDado() == valor) {
				// Este nó não é nulo, é aquele que queremos remover e não possui filhos.
				if (no.getNoEsquerdo() == null && no.getNoDireito() == null) {
					// Este nó não é nulo, é aquele que queremos remover, não possui filhos e é a
					// raiz.
					if (pai == null) {
						raiz = null;
						// Este nó não é nulo, é aquele que queremos remover, não possui filhos, não é a
						// raiz e está a direita do pai.
					} else if (no.getDado() >= pai.getDado()) {
						pai.setNoDireito(null);
						// Este nó não é nulo, é aquele que queremos remover, não possui filhos, não é a
						// raiz e está a esquerda do pai.
					} else {
						pai.setNoEsquerdo(null);
					}
					// Este nó não é nulo, é aquele que queremos remover, só possui filho a direita.
				} else if (no.getNoEsquerdo() == null) {
					// Este nó não é nulo, é aquele que queremos remover, só possui filho a direita
					// e está a direita do pai.
					if (no.getDado() >= pai.getDado()) {
						pai.setNoDireito(no.getNoDireito());
						// Este nó não é nulo, é aquele que queremos remover, só possui filho a direita
						// e está a esquerda do pai.
					} else {
						pai.setNoEsquerdo(no.getNoDireito());
					}
					// DÚVIDA NÃO ENTENDI A NECESSIDADE DE DIZER QUE LEFT DO NÓ É NULL, POIS JÁ É
					// NULL.
					no.setNoEsquerdo(null);
					// Este nó não é nulo, é aquele que queremos remover, só possui filho a
					// esquerda.
				} else if (no.getNoDireito() == null) {
					// Este nó não é nulo, é aquele que queremos remover, só possui filho a esquerda
					// e está a direita do pai.
					if (no.getDado() >= pai.getDado()) {
						pai.setNoDireito(no.getNoEsquerdo());
						// Este nó não é nulo, é aquele que queremos remover, só possui filho a esquerda
						// e está a esquerda do pai.
					} else {
						pai.setNoEsquerdo(no.getNoEsquerdo());
					}
					// DUVIDA NAO ENTENDI A NECESSIDADE DE DIZER QUE LEFT É NULL.
					no.setNoEsquerdo(null);
					// Este nó não é nulo, é aquele que queremos remover, possui filho a direita e a
					// esquerda.
				} else {
					// Obtém o valor do maior nó a esquerda do nó que queremos remover.
					Integer maior = maiorEsquerda(no).getDado();
					// Remove o nó com o valor que acabamos de achar.
					removerNo(no, maior, pai);
					// O nó do início que queríamos remover obtém o valor do maior nó a esquerda.
					no.setDado(maior);

				}
				// Valor que estou procurando remover não é igual ao valor do nó atual, mas é
				// maior.
			} else if (valor > no.getDado()) {
				removerNo(no.getNoDireito(), valor, no);
				// Valor que estou procurando remover não é igual ao valor do nó atual, mas é
				// menor.
			} else {
				removerNo(no.getNoEsquerdo(), valor, no);
			}
		}
		return null;
	}

	private NoArvoreAVL maiorEsquerda(NoArvoreAVL no) {
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

	static int buscarAltura(NoArvoreAVL no) {
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
