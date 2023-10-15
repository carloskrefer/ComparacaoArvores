package com.krefer;

import java.util.Random;
import java.util.Scanner;

public class Main {
	private static Random gerador_numero_aleatorios_com_semente;
	private static TipoArvore tipoArvore;
	private static TipoOperacao tipoOperacao;
	private static int qtdOperacao;
	private static boolean deveraImprimirArvore;
	private static int limiteValorAleatorio;
	private static TipoExecucao tipoExecucao;
	private static int tempoEsperaSegundos;
	private static Scanner scanner;
	
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
		
		switch(tipoExecucao) {
		case TestePerformanceAutomatico:
			executarTestesPerformanceAutomaticos();
			break;
		case Manual:
			executarProgramaManualmente();
			break;
		}
	}
	
	private static void executarProgramaManualmente() {
		scanner = new Scanner(System.in);
		String comandoSelecionado;
		Integer dadoSelecionado;
		boolean isBuscaComSucesso;
		
		switch(tipoArvore) {
		case AVL:
			ArvoreBinariaAVL arvoreAVL = new ArvoreBinariaAVL();
			while (true) {
				System.out.println("Insira um comando (<i=inserir/b=buscar/r=remover/s=sair>):");
				comandoSelecionado = solicitarComandoUsuario(new String[] {"i", "b", "r", "s"});
				if (comandoSelecionado.equals("s")) {
					break;
				}
				System.out.println("\nInforme um número natural menor que 1000:");
				dadoSelecionado = solicitarDadoUsuario();
				
				switch(comandoSelecionado) {
				case "i":
					arvoreAVL.inserir(dadoSelecionado);
					break;
				case "b":
					isBuscaComSucesso = arvoreAVL.buscar(dadoSelecionado);
					if (isBuscaComSucesso) { 
						System.out.println("Valor localizado!"); 
					} else {
						System.out.println("Valor não localizado...");
					}
					break;
				case "r":
					arvoreAVL.remover(dadoSelecionado);
				}
				System.out.println("\nImpressão da árvore AVL:");
				arvoreAVL.imprimir();		
				System.out.println();
			}
			break;
		case Comum:
			ArvoreBinaria arvoreComum = new ArvoreBinaria();
			while (true) {
				System.out.println("Insira um comando (<i=inserir/b=buscar/r=remover/s=sair>):");
				comandoSelecionado = solicitarComandoUsuario(new String[] {"i", "b", "r", "s"});
				if (comandoSelecionado.equals("s")) {
					break;
				}
				System.out.println("\nInforme um número natural menor que 1000:");
				dadoSelecionado = solicitarDadoUsuario();
				
				switch(comandoSelecionado) {
				case "i":
					arvoreComum.inserir(dadoSelecionado);
					break;
				case "b":
					isBuscaComSucesso = arvoreComum.buscar(dadoSelecionado);
					if (isBuscaComSucesso) { 
						System.out.println("Valor localizado!"); 
					} else {
						System.out.println("Valor não localizado...");
					}
					break;
				case "r":
					arvoreComum.remover(dadoSelecionado);
				}
				System.out.println("\nImpressão da árvore comum:");
				arvoreComum.imprimir();
				System.out.println();
			}
			break;
		}
	}
	
	private static Integer solicitarDadoUsuario() {
		String valorSelecionado;
		boolean isEntradaValida;
		
		do {
			valorSelecionado = scanner.nextLine();
			isEntradaValida = validarDadoUsuario(valorSelecionado);
		} while (!isEntradaValida);
		
		return Integer.parseInt(valorSelecionado);
	} 
	
	private static boolean validarDadoUsuario(String valorSelecionado) {
		try {
			Integer valorSelecionadoInteiro = Integer.parseInt(valorSelecionado);
			if ((valorSelecionadoInteiro > 999) || (valorSelecionadoInteiro < 0)) {
				throw new Exception();
			}
			return true;
		} catch (Exception e) {
			System.out.println("Valor selecionado inválido! Tente novamente.");
		}		
		return false;
	}
	
	private static String solicitarComandoUsuario(String[] opcoesValidas) {
		String opcaoSelecionada;
		boolean isEntradaValida;
		
		do {
			opcaoSelecionada = scanner.nextLine();
			isEntradaValida = validarOpcaoSelecionada(opcoesValidas, opcaoSelecionada);
		} while (!isEntradaValida);
		
		return opcaoSelecionada;
	}
	
	private static boolean validarOpcaoSelecionada(String[] opcoesValidas, String opcaoSelecionada) {
		for (int i = 0; i < opcoesValidas.length; i++) {
			if (opcoesValidas[i] == null) {
				continue;
			} else if (opcaoSelecionada.equals(opcoesValidas[i])) {
				return true;
			}
		}
		System.out.println("Opção selecionada não existe! Tente novamente.");
		return false;
	}
	
	private static void executarTestesPerformanceAutomaticos() {
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
			tipoExecucao = (args[0].equals("t")) ? TipoExecucao.TestePerformanceAutomatico : TipoExecucao.Manual;
			tipoArvore = (args[1].equals("c")) ? TipoArvore.Comum : TipoArvore.AVL;
			if (tipoExecucao.equals(TipoExecucao.TestePerformanceAutomatico)) {
				tipoOperacao = (args[2].equals("i")) ? TipoOperacao.Insercao : (args[1].equals("r")) ? TipoOperacao.Remocao : TipoOperacao.Busca;
				qtdOperacao = (Integer.parseInt(args[3]));
				deveraImprimirArvore = (args[4].equals("s")) ? true : false;
				gerador_numero_aleatorios_com_semente = new Random(Long.parseLong(args[5]));
				limiteValorAleatorio = Integer.parseInt(args[6]);
				tempoEsperaSegundos = Integer.parseInt(args[7]);
				if ((limiteValorAleatorio > 1000) || (tempoEsperaSegundos > 60) || (tempoEsperaSegundos < 0)) { throw new Exception(); }
			}
		} catch(Exception e) {
			System.out.println("\nArgumentos inválidos. Reinicie a aplicação.\n");
			System.out.println("Argumentos válidos para execução manual (nesta ordem, todos obrigatórios): ");
			System.out.println("<m=manual> <c=arvore comum/a=arvore avl>");
			System.out.println("Ex: m a");
			System.out.println("Explicação: irá gerar uma árvore AVL para execução manual, sendo depois solicitado os inputs do usuário para inserir, remover ou buscar.\n");
			System.out.println("Argumentos válidos para testes automáticos (nesta ordem, todos obrigatórios): ");
			System.out.println("<t=teste automatico> <c=arvore comum/a=arvore avl> <i=insercao/r=remocao/b=busca> <inteiro=qtd operações> \n"
					+ "<s=imprimir árvore/n=não imprimir árvore> <inteiro=semente> <inteiro=limite valor aleatório - 1, máx. 1000> \n"
					+ "<inteiro positivo incluindo zero=tempo espera para abrir jconsole, mín. 0, máx. 60>");
			System.out.println("Ex: t c i 10 s 1 20 3");
			System.out.println("Explicação: irá gerar uma árvore comum para teste de performance, irá inserir 10 valores aleatórios nela de acordo com a semente 1, \n"
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
