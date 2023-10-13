package com.krefer;

import com.krefer.utils.impressoras.ImpressoraArvoreAVL;
import com.krefer.utils.pilha_dinamica.Pilha;

public class ArvoreBinariaAVL {
	private NoArvoreAVL raiz;

	public NoArvoreAVL getRaiz() {
		return raiz;
	}

	// Método que o usuário acessa para inserir um valor na árvore.
	public void inserir(int dado) {
		NoArvoreAVL noPaiPercorrido;
		Pilha<NoArvoreAVL> pilhaNosPaisPercorridos = new Pilha<NoArvoreAVL>();
		inserir(raiz, dado, pilhaNosPaisPercorridos); 
		boolean rotacaoOcorreu = false;
		Pilha<NoArvoreAVL> pilhaRecalculoFatorBalanceamento = new Pilha<NoArvoreAVL>();
		
		// Esse loop while só serve pra achar o pai mais próximo do nó inserido que
		// passou a ter balanceamento > 1 ou < -1 após a inserção, a fim de 
		// realizar a rotação nele e se necessário nos seus filhos também (casos particulares).
		// Mas isso não garante que após a rotação eles terão um fator de balanceamento
		// correto. Pra isso eu adicionei um loop while lá no final que confere todos eles (
		// percebi que no caso de adição com ou sem rotação só precisa recalcular o balanceamento
		// deles).
		while (!pilhaNosPaisPercorridos.estaVazia()) {
			noPaiPercorrido = pilhaNosPaisPercorridos.remover().getDado();
			pilhaRecalculoFatorBalanceamento.inserir(noPaiPercorrido);
			noPaiPercorrido.atualizarFatorBalanceamento();
			if (Math.abs(noPaiPercorrido.getFatorBalanceamento()) > 1) {
				realizarRotacoes(noPaiPercorrido);
				rotacaoOcorreu = true;
				break;
			}
		}
		
		// 
		while (!pilhaRecalculoFatorBalanceamento.estaVazia()) {
			pilhaRecalculoFatorBalanceamento.remover().getDado().atualizarFatorBalanceamento();
		}
	}
	
	private void realizarRotacoes(NoArvoreAVL no) {
		NoArvoreAVL filhoComFatorMaisUm =  obterFilhoComFatorEspecificado(1, no);
		NoArvoreAVL filhoComFatorMenosUm = obterFilhoComFatorEspecificado(-1, no);
		boolean haFilhoComFatorMaisUm =  filhoComFatorMaisUm != null;
		boolean haFilhoComFatorMenosUm = filhoComFatorMenosUm != null;
		
		switch (no.getFatorBalanceamento()) {
			case -2:
				if (haFilhoComFatorMenosUm) {
					realizarRotacaoEsquerda(no);
				}
				break;
			case 2:
				if (haFilhoComFatorMaisUm) {
					realizarRotacaoDireita(no);
				}
				break;
		}
	}
	
	private void realizarRotacaoEsquerda(NoArvoreAVL no) {
		NoArvoreAVL novaRaizSubarvore = no.getNoDireito();
		NoArvoreAVL antigoNoEsquerdoDaNovaRaizSubArvore = novaRaizSubarvore.getNoEsquerdo();
		novaRaizSubarvore.setNoEsquerdo(no);
		no.setNoDireito(antigoNoEsquerdoDaNovaRaizSubArvore);
		
		if (no == raiz) {
			raiz = novaRaizSubarvore;
		} else {
			NoArvoreAVL noPaiDoNoRotacionado = buscarNoPai(no);
			if (noPaiDoNoRotacionado.getNoDireito() == no) {
				noPaiDoNoRotacionado.setNoDireito(novaRaizSubarvore);
			} else {
				noPaiDoNoRotacionado.setNoEsquerdo(novaRaizSubarvore);
			}
		}
	}
	
	private void realizarRotacaoDireita(NoArvoreAVL no) {
		NoArvoreAVL novaRaizSubarvore = no.getNoEsquerdo();
		NoArvoreAVL antigoNoDireitoDaNovaRaizSubArvore = novaRaizSubarvore.getNoDireito();
		novaRaizSubarvore.setNoDireito(no);
		no.setNoEsquerdo(antigoNoDireitoDaNovaRaizSubArvore);
		
		if (no == raiz) {
			raiz = novaRaizSubarvore;
		} else {
			NoArvoreAVL noPaiDoNoRotacionado = buscarNoPai(no);
			if (noPaiDoNoRotacionado.getNoDireito() == no) {
				noPaiDoNoRotacionado.setNoDireito(novaRaizSubarvore);
			} else {
				noPaiDoNoRotacionado.setNoEsquerdo(novaRaizSubarvore);
			}
		}
	}
	
	private NoArvoreAVL obterFilhoComFatorEspecificado(int fatorBuscado, NoArvoreAVL noPai) {
		if ((noPai.getNoDireito() != null) && (noPai.getNoDireito().getFatorBalanceamento() == fatorBuscado)) {
			return noPai.getNoDireito();
		} else if ((noPai.getNoEsquerdo() != null) && (noPai.getNoEsquerdo().getFatorBalanceamento() == fatorBuscado)) {
			return noPai.getNoEsquerdo();
		} else {
			return null;
		}
	}

	// listaNosPercorridos são os pais (ou avôs, bisavôs, etc) do nó inserido que foram
	// percorridos até achar o lugar para inserí-lo. Serve pra eu saber quem que precisará
	// ter seu fator de balanceamento recalculado.
	private void inserir(NoArvoreAVL no, int dado, Pilha<NoArvoreAVL> pilhaNosPaisPercorridos) {
		if (raiz == null) {
			raiz = new NoArvoreAVL(dado);
		} else if (dado >= no.getDado()) {
			if (no.getNoDireito() == null) {
				pilhaNosPaisPercorridos.inserir(no); 
				no.setNoDireito(new NoArvoreAVL(dado));
			} else {
				pilhaNosPaisPercorridos.inserir(no); 
				inserir(no.getNoDireito(), dado, pilhaNosPaisPercorridos);
			}
		} else {
			if (no.getNoEsquerdo() == null) {
				pilhaNosPaisPercorridos.inserir(no); 
				no.setNoEsquerdo(new NoArvoreAVL(dado));
			} else {
				pilhaNosPaisPercorridos.inserir(no); 
				inserir(no.getNoEsquerdo(), dado, pilhaNosPaisPercorridos);
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
	
	private NoArvoreAVL buscarNoPai(NoArvoreAVL noFilho) {
		return buscarNoPai(noFilho, raiz);
	}
	
	private NoArvoreAVL buscarNoPai(NoArvoreAVL noFilhoAlvo, NoArvoreAVL noAtual) {
		if (noFilhoAlvo == raiz) {
			return null;
		}
		if ((noAtual.getNoEsquerdo() == noFilhoAlvo) || (noAtual.getNoDireito() == noFilhoAlvo)) {
			return noAtual;
		}
		
		if (noFilhoAlvo.getDado() < noAtual.getDado()) {
			return (noAtual.getNoEsquerdo() == null) ? null : buscarNoPai(noFilhoAlvo, noAtual.getNoEsquerdo());
		} else {
			return (noAtual.getNoDireito() == null) ? null : buscarNoPai(noFilhoAlvo, noAtual.getNoDireito());
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
