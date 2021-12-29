package ReparacoesUI;

import Middleware.TecnicoJaTemAgendaException;
import ReparacoesLN.*;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MyUI {

	private IReparacoesLN model;

	private Scanner scin;

	/**
	 * Construtor.
	 * <p>
	 * Cria os menus e a camada de negócio.
	 */
	public MyUI() {
		//inicial model
		scin = new Scanner(System.in);
	}

	public void run() {
		System.out.println("Bem vindo ao Sistema DSS-TechSupport!");
		this.menuPrincipal();
		System.out.println("Até breve...");
	}

	private void menuPrincipal() {
		Menu menu = new Menu(new String[]{
				"Autenticar"
		});

		// mais pré-condições?

		// Registar os handlers das transições
		menu.setHandler(1, () -> autenticarColaborador());

		// Executar o menu
		menu.run();
		System.out.println("Guardando...");
		//this.model.save();

	}

	private void autenticarColaborador() {
		System.out.println("Insira o seu número de identificação: ");
		String id = scin.nextLine();
		if (this.model.existeColaborador(id)) {
			System.out.println("Acesso garantido");
			switch (this.model.getColaborador().getClass.getSimpleName()){
				case "Gestor":
					this.menuGestor();
					break;
				case "Tecnico":
					this.menuTecnico();
					break;
				case "FuncionarioBalcao":
					this.menuFuncionarioBalcao();
					break;
			}
		}
	}

	private void menuGestor(){
		Menu menu = new Menu(new String[]{
				"Registar Colaborador",
				"Listar reparações detalhadas por técnico",
				"Listar reparações resumidas por técnico",
				"Listar fluxo de trabalho do funcionário de balcão",
				"Menu Colaborador Especializado"
		});

		// mais pré-condições?

		// Registar os handlers das transições
		menu.setHandler(1, this::registaColaborador);
		menu.setHandler(2, this::listarDetalhadas);
		menu.setHandler(3, this::listarResumidas);
		menu.setHandler(4, this::listarFluxoFunc);
		menu.setHandler(5, this::menuColabEspecial);

		menu.run();
	}


	private void registaColaborador() {
		try {
			System.out.println("Nome do Colaborador: ");
			String nome = scin.nextLine();
			System.out.println("Função do Colaborador: ");
			String tipo = scin.nextLine();
			this.model.registaColaborador(nome, tipo);
			System.out.println("Colaborador adicionado");
		} catch (TecnicoJaTemAgendaException e) {
			e.printStackTrace();
		}
	}

	private void listarDetalhadas() {
		System.out.println("Mês que pretende ser avaliado: ");
		int mes = scin.nextInt();
		System.out.println("Ano: ");
		int ano = scin.nextInt();
		LocalDateTime data = LocalDateTime.of(ano, mes, 1, 1, 1, 1, 1);
		model.getReparacoesMes(data);
	}

	private void listarFluxoFunc() {
	}

	private void listarResumidas() {
	}

	private void menuColabEspecial() {
		Menu menuTurmas = new Menu(new String[]{
				"Criar Reparação Expresso",
				"Criar Passo de Reparação"
		});

		// Registar pré-condições das transições

		// Registar os handlers das transições
		menuTurmas.setHandler(1, this::criarRepXpresso);
		menuTurmas.setHandler(2, this::criarPassoRep);

		menuTurmas.runOnce();
	}

	private void criarRepXpresso() {
	}

	private void criarPassoRep() {
	}

	private void menuTecnico(){
		Menu menu = new Menu(new String[]{
				"Fazer orçamento",
				"Realizar reparação"
		});

		// mais pré-condições?

		// Registar os handlers das transições
		menu.setHandler(1, this::fazerOrcamento);
		menu.setHandler(2, this::realizarReparacao);

		menu.run();
	}

	private void fazerOrcamento() {
	}

	private void realizarReparacao() {
	}

	private void menuFuncionarioBalcao(){
		Menu menu = new Menu(new String[]{
				"Registar cliente",
				"Registar pedido de orçamento",
				"Confirmar orçamento",
				"Registar pedido de Reparação Expresso",
				"Registar entrega do equipamento"
		});

		// mais pré-condições?

		// Registar os handlers das transições
		menu.setHandler(1, this::registaCliente);
		menu.setHandler(2, this::pedidoOrcamento;
		menu.setHandler(3, this::confirmarOrcamento);
		menu.setHandler(4, this::pedidoRepXpresso);
		menu.setHandler(5, this::registarEntregaEquip);

		menu.run();
	}

	private void registaCliente() {
	}

	private void pedidoOrcamento() {
	}

	private void confirmarOrcamento() {
	}

	private void pedidoRepXpresso() {
	}

	private void registarEntregaEquip() {
	}

}