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
//		try {
//			tipoArvore = (args[0].equals("c")) ? TipoArvore.Comum : TipoArvore.AVL;
//			tipoOperacao = (args[1].equals("i")) ? TipoOperacao.Insercao : (args[1].equals("r")) ? TipoOperacao.Remocao : TipoOperacao.Busca;
//			qtdOperacao = (Integer.parseInt(args[2]));
//			deveraImprimirArvore = (args[3].equals("s")) ? true : false;
//			gerador_numero_aleatorios_com_semente = new Random(Long.parseLong(args[4]));
//			limiteValorAleatorio = Integer.parseInt(args[5]);
//			tempoEsperaSegundos = Integer.parseInt(args[6]);
//			if ((limiteValorAleatorio > 1000) || (tempoEsperaSegundos > 60) || (tempoEsperaSegundos < 0)) { throw new Exception(); }
//		} catch(Exception e) {
//			System.out.println("Argumentos inválidos. Reinicie a aplicação.");
//			return;
//		}
//		
//		try { 
//			int timer = tempoEsperaSegundos;
//			for (int i = 0; i < tempoEsperaSegundos; i++) {
//				System.out.println("Inicie o JConsole, o programa iniciará em " + timer + " segundos...");
//				Thread.sleep(1000);
//				timer--;
//			}
//		} catch(Exception e) {}
//		
//		switch(tipoArvore) {
//		case Comum:
//			ArvoreBinaria arvoreComum = new ArvoreBinaria();
//			inserirNumerosAleatorios(arvoreComum);
//			if (deveraImprimirArvore) {
//				System.out.println("Impressão da árvore:");
//				arvoreComum.imprimir();			
//			}
//			if (tipoOperacao.equals(TipoOperacao.Remocao)) {
//				removerNumerosAleatorios(arvoreComum);
//				if (deveraImprimirArvore) {
//					System.out.println("Impressão da árvore:");
//					arvoreComum.imprimir();			
//				}
//			} else if (tipoOperacao.equals(TipoOperacao.Busca)) {
//				buscarNumerosAleatorios(arvoreComum);
//			}
//			break;
//		case AVL:
//			ArvoreBinariaAVL arvoreAVL = new ArvoreBinariaAVL();
//			inserirNumerosAleatorios(arvoreAVL);
//			if (deveraImprimirArvore) {
//				System.out.println("Impressão da árvore:");
//				arvoreAVL.imprimir();			
//			}
//			if (tipoOperacao.equals(TipoOperacao.Remocao)) {
//				removerNumerosAleatorios(arvoreAVL);
//				if (deveraImprimirArvore) {
//					System.out.println("Impressão da árvore:");
//					arvoreAVL.imprimir();			
//				}
//			} else if (tipoOperacao.equals(TipoOperacao.Busca)) {
//				buscarNumerosAleatorios(arvoreAVL);
//			}
//			break;
//		}
		
		ArvoreBinariaAVL a = new ArvoreBinariaAVL();
		a.inserir(20);
		a.inserir(10);
		a.inserir(30);
		a.inserir(8);
		a.inserir(12);

		
		a.imprimir();
//		a.remover(30);
//		a.imprimir();
		
	}
	
	private static void buscarNumerosAleatorios(ArvoreBinaria arvore) {
		for (int i = 0; i < qtdOperacao; i++) {
			arvore.buscar(gerarNumeroAleatorio());
		}
	}
	
	private static void buscarNumerosAleatorios(ArvoreBinariaAVL arvore) {
		for (int i = 0; i < qtdOperacao; i++) {
			arvore.buscar(gerarNumeroAleatorio());
		}
	}
	
	private static void removerNumerosAleatorios(ArvoreBinaria arvore) {
		for (int i = 0; i < qtdOperacao; i++) {
			arvore.remover(gerarNumeroAleatorio());
		}
	}
	
	private static void removerNumerosAleatorios(ArvoreBinariaAVL arvore) {
		for (int i = 0; i < qtdOperacao; i++) {
			arvore.remover(gerarNumeroAleatorio());
		}
	}
	
	private static void inserirNumerosAleatorios(ArvoreBinaria arvore) {
		for (int i = 0; i < qtdOperacao; i++) {
			arvore.inserir(gerarNumeroAleatorio());
		}
	}
	
	private static void inserirNumerosAleatorios(ArvoreBinariaAVL arvore) {
//		int num;
		for (int i = 0; i < qtdOperacao; i++) {
//			num = gerarNumeroAleatorio();
//			System.out.println(num);
			arvore.inserir(gerarNumeroAleatorio());
		}
	}
	
	private static int gerarNumeroAleatorio() {
		return gerador_numero_aleatorios_com_semente.nextInt(limiteValorAleatorio);
	}

}
