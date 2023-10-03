package com.krefer.utils.lista_encadeada;

public class Lista<T> {
	private No<T> primeiroNo;

	public Lista() {
		this.primeiroNo = null;
	}
	
	public No<T> getPrimeiroNo() {
		return primeiroNo;
	}

	public void setPrimeiroNo(No<T> primeiroNo) {
		this.primeiroNo = primeiroNo;
	}
	
	public boolean listaEstaVazia() {
		return (primeiroNo == null);
	}
	
	/* Cria um novo nó e insere no final da lista. Este novo nó
	 * terá o dado informado. */
	public void inserir(T dado) {
		if (listaEstaVazia()) {
			primeiroNo = new No<T>(dado);
		} else {
			buscarUltimoNo(primeiroNo).setProximoNo(new No<T>(dado));
		}
	}
	
	/* Insere o nó informado no final da lista. */
	public void inserir(No<T> no) {
		if (listaEstaVazia()) {
			primeiroNo = no;
		} else {
			buscarUltimoNo(primeiroNo).setProximoNo(no);
		}
	}
	
	/* Imprime todos os elementos da lista encadeada. 
	 * Não chamar este método se a lista estiver vazia.
	 * Caso contrário, será lançada exceção. */
	public void imprimir() {
		if (listaEstaVazia()) {
			System.out.println("A estrutura está vazia.");
		} else {
			imprimir(primeiroNo);
		}
	}
	
	/* Imprime todos os elementos da lista encadeada. 
	 * Parâmetro no: primeiro nó da lista.
	 * Não chamar este método se a lista estiver vazia.
	 * Caso contrário, será lançada exceção. */
	private No<T> imprimir(No<T> no) {
		System.out.print(no.getDado() + " -> ");
		if (no.getProximoNo() == null) {
			System.out.println("null");
			return no;
		} else {
			return imprimir(no.getProximoNo());
		}
	}
	
	/* Busca o último nó da lista encadeada.
	 * Retorno: último nó da lista.
	 * Não chamar este método se a lista estiver vazia.
	 * Caso contrário, será lançada exceção. */
	public No<T> buscarUltimoNo() {
		return buscarUltimoNo(primeiroNo);
	}
	
	/* Busca o último nó da lista encadeada.
	 * Parâmetro no: primeiro nó da lista.
	 * Retorno: último nó da lista.
	 * Não chamar este método se a lista estiver vazia.
	 * Caso contrário, será lançada exceção. */
	private No<T> buscarUltimoNo(No<T> no) {
		if (no.getProximoNo() == null) {
			return no;
		} else {
			return buscarUltimoNo(no.getProximoNo());
		}
	}
	
	/* Busca o nó anterior ao nó alvo. Ex:
	 * 		a -> b -> c
	 * 		Se o nó alvo é c, então será retornado o nó b.
	 * Parâmetro noAlvo: nó alvo.
	 * Retorno: nó anterior ao nó alvo.
	 * Não chamar este método se: a) a lista estiver vazia
	 * b) a lista só tem um único nó. Caso contrário,
	 * será lançada exceção. */
	public No<T> buscarNoAnteriorAoNoAlvo(No<T> noAlvo) {
		return buscarNoAnteriorAoNoAlvo(primeiroNo, noAlvo);
	}
	
	/* Busca o nó anterior ao nó alvo. Ex:
	 * 		a -> b -> c
	 * 		Se o nó alvo é c, então será retornado o nó b.
	 * Parâmetro no: primeiro nó da lista (o topo).
	 * Parâmetro noAlvo: nó alvo.
	 * Retorno: nó anterior ao nó alvo.
	 * Não chamar este método se: a) a lista estiver vazia
	 * b) a lista só tem um único nó. Caso contrário,
	 * será lançada exceção. */
	private No<T> buscarNoAnteriorAoNoAlvo(No<T> no, No<T> noAlvo) {
		if (no.getProximoNo() == noAlvo) {
			return no;
		} else {
			return buscarNoAnteriorAoNoAlvo(no.getProximoNo(), noAlvo);
		}
	}
	
	/* Verifica se a pilha possui apenas um elemento.
	 * Retorno: true se possui apenas um elemento. Caso contrário, false.
	 * Não chamar este método se a lista estiver vazia.
	 * Caso contrário, será lançada exceção. */
	public boolean possuiApenasUmElemento() {
		return (buscarUltimoNo() == getPrimeiroNo());
	}
}
