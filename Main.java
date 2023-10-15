package com.krefer;

import java.util.Random;

public class Main {
	private static Random gerador_numero_aleatorios_com_semente;
	private static TipoArvore tipoArvore;
	private static TipoOperacao tipoOperacao;
	private static int qtdOperacao;
	private static boolean deveraImprimirArvore;
	private static int limiteValorAleatorio;
	private static TipoExecucao tipoExecucao;
	private static int tempoEsperaSegundos;
	
	private enum TipoArvore { Comum, AVL }
	private enum TipoOperacao { Insercao, Remocao, Busca }
	private enum TipoExecucao { TestePerformanceAutomatico, Manual } 
	
	public static void main(String[] args) {		
		try {
			validarInicializarEntradas(args);
		} catch (IllegalArgumentException e) {
			return;
		}
		
		aguardarTempoSolicitado();
		
		switch(tipoArvore) {
		case Comum:
			ArvoreBinaria arvoreComum = new ArvoreBinaria();
			inserirNumerosAleatorios(arvoreComum);
			if (deveraImprimirArvore) {
				System.out.println("Impressão da árvore:");
				arvoreComum.imprimir();			
			}
			if (tipoOperacao.equals(TipoOperacao.Remocao)) {
				removerNumerosAleatorios(arvoreComum);
				if (deveraImprimirArvore) {
					System.out.println("Impressão da árvore:");
					arvoreComum.imprimir();			
				}
			} else if (tipoOperacao.equals(TipoOperacao.Busca)) {
				buscarNumerosAleatorios(arvoreComum);
			}
			break;
		case AVL:
			ArvoreBinariaAVL arvoreAVL = new ArvoreBinariaAVL();
			inserirNumerosAleatorios(arvoreAVL);
			if (deveraImprimirArvore) {
				System.out.println("Impressão da árvore:");
				arvoreAVL.imprimir();			
			}
			if (tipoOperacao.equals(TipoOperacao.Remocao)) {
				removerNumerosAleatorios(arvoreAVL);
				if (deveraImprimirArvore) {
					System.out.println("Impressão da árvore:");
					arvoreAVL.imprimir();			
				}
			} else if (tipoOperacao.equals(TipoOperacao.Busca)) {
				buscarNumerosAleatorios(arvoreAVL);
			}
			break;
		}
	}
	
	private static void aguardarTempoSolicitado() {	
		try { 
			int timer = tempoEsperaSegundos;
			for (int i = 0; i < tempoEsperaSegundos; i++) {
				System.out.println("Inicie o JConsole, o programa iniciará em " + timer + " segundos...");
				Thread.sleep(1000);
				timer--;
			}
		} catch(Exception e) {}
	}
	
	private static void validarInicializarEntradas(String args[]) throws IllegalArgumentException {
		try {
			tipoArvore = (args[0].equals("c")) ? TipoArvore.Comum : TipoArvore.AVL;
			tipoOperacao = (args[1].equals("i")) ? TipoOperacao.Insercao : (args[1].equals("r")) ? TipoOperacao.Remocao : TipoOperacao.Busca;
			qtdOperacao = (Integer.parseInt(args[2]));
			deveraImprimirArvore = (args[3].equals("s")) ? true : false;
			gerador_numero_aleatorios_com_semente = new Random(Long.parseLong(args[4]));
			limiteValorAleatorio = Integer.parseInt(args[5]);
			tempoEsperaSegundos = Integer.parseInt(args[6]);
			if ((limiteValorAleatorio > 1000) || (tempoEsperaSegundos > 60) || (tempoEsperaSegundos < 0)) { throw new Exception(); }
		} catch(Exception e) {
			System.out.println("Argumentos inválidos. Reinicie a aplicação.");
			System.out.println("Argumentos válidos (nesta ordem, todos obrigatórios): ");
			System.out.println("<c=arvore comum/a=arvore avl> <i=insercao/r=remocao/b=busca> <inteiro=qtd operações> \n"
					+ "<s=imprimir árvore/n=não imprimir árvore> <inteiro=semente> <inteiro=limite valor aleatório - 1, máx. 1000> \n"
					+ "<inteiro positivo incluindo zero=tempo espera para abrir jconsole, mín. 0, máx. 60>");
			System.out.println("Ex: c i 10 s 1 20 3");
			System.out.println("Irá gerar uma árvore comum, irá inserir 10 valores aleatórios nela de acordo com a semente 1, \n"
					+ "deverá imprimir a árvore, os números aleatórios variam de 0 à 19 e o sistema aguardará 3 segundos antes de iniciar.");
			throw new IllegalArgumentException();
		}
	}
 	
	private static void buscarNumerosAleatorios(ArvoreBinaria arvore) {
		int num;
		int contadorAcertos = 0;
		boolean isBuscaComSucesso;
		System.out.print("Valores a buscar: ");
		for (int i = 0; i < qtdOperacao; i++) {
			num = gerarNumeroAleatorio();
			System.out.print(num + " ");
			isBuscaComSucesso = arvore.buscar(num);
			if (isBuscaComSucesso) {
				contadorAcertos++;
			}
		}
		System.out.println();
		System.out.println("Quantidade de valores encontrados com sucesso: " + contadorAcertos);
	}
	
	private static void buscarNumerosAleatorios(ArvoreBinariaAVL arvore) {
		int num;
		int contadorAcertos = 0;
		boolean isBuscaComSucesso;
		System.out.print("Valores a buscar: ");
		for (int i = 0; i < qtdOperacao; i++) {
			num = gerarNumeroAleatorio();
			System.out.print(num + " ");
			isBuscaComSucesso = arvore.buscar(num);
			if (isBuscaComSucesso) {
				contadorAcertos++;
			}
		}
		System.out.println();
		System.out.println("Quantidade de valores encontrados com sucesso: " + contadorAcertos);
	}
	
	private static void removerNumerosAleatorios(ArvoreBinaria arvore) {
		int num;
		System.out.print("Valores a remover: ");
		for (int i = 0; i < qtdOperacao; i++) {
			num = gerarNumeroAleatorio();
			System.out.print(num + " ");
			arvore.remover(num);
		}
		System.out.println();
	}
	
	private static void removerNumerosAleatorios(ArvoreBinariaAVL arvore) {
		int num;
		System.out.print("Valores a remover: ");
		for (int i = 0; i < qtdOperacao; i++) {
			num = gerarNumeroAleatorio();
			System.out.print(num + " ");
			arvore.remover(num);
		}
		System.out.println();
	}
	
	private static void inserirNumerosAleatorios(ArvoreBinaria arvore) {
		int num;
		System.out.print("Valores a inserir: ");
		for (int i = 0; i < qtdOperacao; i++) {
			num = gerarNumeroAleatorio();
			System.out.print(num + " ");
			arvore.inserir(num);
		}
		System.out.println();
	}
	
	private static void inserirNumerosAleatorios(ArvoreBinariaAVL arvore) {
		int num;
		System.out.print("Valores a inserir: ");
		for (int i = 0; i < qtdOperacao; i++) {
			num = gerarNumeroAleatorio();
			System.out.print(num + " ");
			arvore.inserir(num);
		}
		System.out.println();
	}
	
	private static int gerarNumeroAleatorio() {
		return gerador_numero_aleatorios_com_semente.nextInt(limiteValorAleatorio);
	}

}
