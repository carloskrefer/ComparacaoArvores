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
		Pilha<NoArvoreAVL> pilhaRecalculoFatorBalanceamento = new Pilha<NoArvoreAVL>();
		NoArvoreAVL noPaiDoNoPaiPercorrido;
		
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
			noPaiDoNoPaiPercorrido = (pilhaNosPaisPercorridos.getTopo() == null) ? null : pilhaNosPaisPercorridos.getTopo().getDado();
			if (Math.abs(noPaiPercorrido.getFatorBalanceamento()) > 1) {
				realizarRotacoes(noPaiPercorrido, noPaiDoNoPaiPercorrido);
				break;
			}
		}
		
		while (!pilhaRecalculoFatorBalanceamento.estaVazia()) {
			pilhaRecalculoFatorBalanceamento.remover().getDado().atualizarFatorBalanceamento();
		}
	}
	
	// O parâmetro paiNo só é necessário por causa de um caso particular, quando subárvores da esquerda
	// passam a ter valores iguais ao da raiz.
	// Isso ocorre da seguinte maneira:
	//        8
	//             9     
	// Até aqui tudo bem, mas se adicionar o 8 novamente:
	//        8
	//             9    
	//           8
	// Agora, vai precisar de dupla rotação. Rotação do filho pra direita:
	//        8
	//             8
	//               9
	// Mas... agora faz rotação do pai pra esquerda:
	//        8
	//    8       9
	// Veja que agora um número igual à raiz se encontra a sua esquerda. 
	// Por causa disso, não funcionou mais meu método de buscar um nó pai de um nó alvo (buscando o objeto em si).
	// Dessa maneira, este método passa a precisar do parâmetro paiNo, já que eu não consigo mais buscá-lo de maneira
	// eficiente. Esse paiNo é usado no realizarRotacaoEsquerda() e realizarRotacaoDireita() pra 
	// fazer o antigo pai, antes da rotação, passar a apontar pra nova raiz, após a rotação.
	private void realizarRotacoes(NoArvoreAVL no, NoArvoreAVL paiNo) {
		NoArvoreAVL filhoQueSofreuInsercao = obterFilhoQueSofreuInsercao(no);
		
		boolean isFatorFilhoQueSofreuInsercaoMaisUm  = filhoQueSofreuInsercao.getFatorBalanceamento() ==  1;
		boolean isFatorFilhoQueSofreuInsercaoMenosUm = filhoQueSofreuInsercao.getFatorBalanceamento() == -1;
		
		switch (no.getFatorBalanceamento()) {
			case -2:
				if (isFatorFilhoQueSofreuInsercaoMaisUm) {
					realizarRotacaoDireita(filhoQueSofreuInsercao, no);
					realizarRotacaoEsquerda(no, paiNo);
				} else {
					realizarRotacaoEsquerda(no, paiNo);
				}
				break;
			case 2:
				if (isFatorFilhoQueSofreuInsercaoMenosUm) {
					realizarRotacaoEsquerda(filhoQueSofreuInsercao, no);
					realizarRotacaoDireita(no, paiNo);
				} else {
					realizarRotacaoDireita(no, paiNo);
				}
				break;
		}
	}
	
	private NoArvoreAVL obterFilhoQueSofreuInsercao(NoArvoreAVL noPai) {		
		int alturaNoEsquerdo = buscarAltura(noPai.getNoEsquerdo());
		int alturaNoDireito  = buscarAltura(noPai.getNoDireito());
		
		if (alturaNoEsquerdo > alturaNoDireito) {
			return noPai.getNoEsquerdo();
		} else {
			return noPai.getNoDireito();
		}
	}
	
	private void realizarRotacaoEsquerda(NoArvoreAVL no, NoArvoreAVL paiNo) {
		NoArvoreAVL novaRaizSubarvore = no.getNoDireito();
		NoArvoreAVL antigoNoEsquerdoDaNovaRaizSubArvore = novaRaizSubarvore.getNoEsquerdo();
		novaRaizSubarvore.setNoEsquerdo(no);
		no.setNoDireito(antigoNoEsquerdoDaNovaRaizSubArvore);
		
		if (no == raiz) {
			raiz = novaRaizSubarvore;
		} else {
			NoArvoreAVL noPaiDoNoRotacionado = paiNo;
			if (noPaiDoNoRotacionado.getNoDireito() == no) {
				noPaiDoNoRotacionado.setNoDireito(novaRaizSubarvore);
			} else {
				noPaiDoNoRotacionado.setNoEsquerdo(novaRaizSubarvore);
			}
		}
	}
	
	private void realizarRotacaoDireita(NoArvoreAVL no, NoArvoreAVL paiNo) {
		NoArvoreAVL novaRaizSubarvore = no.getNoEsquerdo();
		NoArvoreAVL antigoNoDireitoDaNovaRaizSubArvore = novaRaizSubarvore.getNoDireito();
		novaRaizSubarvore.setNoDireito(no);
		no.setNoEsquerdo(antigoNoDireitoDaNovaRaizSubArvore);
		
		if (no == raiz) {
			raiz = novaRaizSubarvore;
		} else {
			NoArvoreAVL noPaiDoNoRotacionado = paiNo;
			if (noPaiDoNoRotacionado.getNoDireito() == no) {
				noPaiDoNoRotacionado.setNoDireito(novaRaizSubarvore);
			} else {
				noPaiDoNoRotacionado.setNoEsquerdo(novaRaizSubarvore);
			}
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

	public boolean buscar(int valor) {
		if (raiz == null) {
			return false;
		} else {
			return buscar(valor, raiz);
		}
	}

	// Não chamar quando raiz for nula
	private boolean buscar(int valorBuscado, NoArvoreAVL no) {
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
	
	// Métodos não mais usados por causa que não é possível buscar o pai de um nó (buscando o objeto em si
	// ao invés de buscar qualquer nó que tenha o mesmo valor) numa árvore em que se repete valores e permite
	// rotações. Ver explicação no método realizarRotacoes().
//	private NoArvoreAVL buscarNoPai(NoArvoreAVL noFilho) {
//		return buscarNoPai(noFilho, raiz);
//	}
//	
//	private NoArvoreAVL buscarNoPai(NoArvoreAVL noFilhoAlvo, NoArvoreAVL noAtual) {
//		if (noFilhoAlvo == raiz) {
//			return null;
//		}
//		if ((noAtual.getNoEsquerdo() == noFilhoAlvo) || (noAtual.getNoDireito() == noFilhoAlvo)) {
//			return noAtual;
//		}
//		
//		if (noFilhoAlvo.getDado() < noAtual.getDado()) {
//			return (noAtual.getNoEsquerdo() == null) ? null : buscarNoPai(noFilhoAlvo, noAtual.getNoEsquerdo());
//		} else {
//			return (noAtual.getNoDireito() == null) ? null : buscarNoPai(noFilhoAlvo, noAtual.getNoDireito());
//		}
//	}

	public void imprimir() {
		new ImpressoraArvoreAVL(this).imprimir();
	}

	public void remover(Integer valor) {
		NoArvoreAVL noPercorrido;
		NoArvoreAVL noPaiDoNoPercorrido;
		Pilha<NoArvoreAVL> pilhaNosPercorridos = new Pilha<NoArvoreAVL>();
		remover(raiz, valor, null, pilhaNosPercorridos);
		
		while (!pilhaNosPercorridos.estaVazia()) {
			noPercorrido = pilhaNosPercorridos.remover().getDado();
			noPercorrido.atualizarFatorBalanceamento();
			noPaiDoNoPercorrido = (pilhaNosPercorridos.getTopo() == null) ? null : pilhaNosPercorridos.getTopo().getDado();
			if (Math.abs(noPercorrido.getFatorBalanceamento()) > 1) {				
				realizarRotacoes(noPercorrido, noPaiDoNoPercorrido);
				break;
			}
		}
		// Isso faz a AVL perder muita performance
		// Vou deixar de exibir os fatores na impressão (eles poderão estar desatualizados, mas isso não é problema
		// pois antes de checá-los eles são atualizados) para evitar essa perda.
//		recalcularTodosFatores(); 

	}
	
//	private void recalcularTodosFatores() {
//		recalcularTodosFatores(raiz);
//	}
	
//	private void recalcularTodosFatores(NoArvoreAVL noAtual) {
//		noAtual.atualizarFatorBalanceamento();
//		if (noAtual.getNoEsquerdo() != null) {
//			recalcularTodosFatores(noAtual.getNoEsquerdo());
//		} 
//		if (noAtual.getNoDireito() != null) {
//			recalcularTodosFatores(noAtual.getNoDireito());
//		}
//	}

	private void remover(NoArvoreAVL noPercorrido, Integer valorRemover, NoArvoreAVL paiNoPercorrido, Pilha<NoArvoreAVL> pilhaNosPercorridos) {
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
					NoArvoreAVL noMaiorEsquerda = buscarMaiorNoEsquerda(noPercorrido);

					pilhaNosPercorridos.inserir(noPercorrido);
					
					remover(noPercorrido.getNoEsquerdo(), noMaiorEsquerda.getDado(), noPercorrido, pilhaNosPercorridos);
					
					// O nó do início que queríamos remover obtém o valor do maior nó a esquerda (ele que de fato foi removido).
					noPercorrido.setDado(noMaiorEsquerda.getDado());
				}
			// Valor que estou procurando remover não é igual ao valor do nó atual, mas é
			// maior.
			} else if (valorRemover > noPercorrido.getDado()) {
				pilhaNosPercorridos.inserir(noPercorrido);
				remover(noPercorrido.getNoDireito(), valorRemover, noPercorrido, pilhaNosPercorridos);
			// Valor que estou procurando remover não é igual ao valor do nó atual, mas é
			// menor.
			} else {
				pilhaNosPercorridos.inserir(noPercorrido);
				remover(noPercorrido.getNoEsquerdo(), valorRemover, noPercorrido, pilhaNosPercorridos);
			}
		}
	}

	private NoArvoreAVL buscarMaiorNoEsquerda(NoArvoreAVL no) {
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
