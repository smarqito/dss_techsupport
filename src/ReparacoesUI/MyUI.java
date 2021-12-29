package ReparacoesUI;

import Middleware.ClienteJaExisteException;
import Middleware.EquipamentoNaoExisteException;
import Middleware.EstadoOrcNaoEValidoException;
import Middleware.TecnicoJaTemAgendaException;
import ReparacoesLN.*;
import ReparacoesLN.SSClientes.Cliente;
import ReparacoesLN.SSClientes.EstadoEquipamento;
import ReparacoesLN.SSReparacoes.OrcamentoEstado;
import ReparacoesLN.SSReparacoes.ReparacaoEstado;

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
				"Realizar reparação",
				"Menu Colaborador Especializado"
		});

		// pré-condições
		//listas de orcamentos não vazia
		//agenda nao vazia

		// Registar os handlers das transições
		menu.setHandler(1, this::fazerOrcamento);
		menu.setHandler(2, this::realizarReparacao);
		menu.setHandler(3, this::menuColabEspecial);

		menu.run();
	}

	private void fazerOrcamento() {

		//get orcamento mais antigo
		//print de algumas informações do orcamento
		String orcId = null;

		Menu menu = new Menu(new String[]{
				"Definir plano de trabalhos"
		});
		// pré-condições
		//listas de orcamentos não vazia
		//agenda nao vazia

		// Registar os handlers das transições
		menu.setHandler(1, () -> definirPLano(orcId));

		menu.runOnce();
	}

	private void definirPLano(String orcId) {
		Menu menu = new Menu(new String[]{
				"Adicionar passo de reparação",
				"Gerar Orcamento",
				"Registar comunicação com o cliente"
		});

		// pré-condições
		//listas de orcamentos não vazia
		//agenda nao vazia

		// Registar os handlers das transições
		menu.setHandler(1, () -> adicionarPasso(orcId));
		menu.setHandler(2, () -> gerarOrc(orcId));
		menu.setHandler(3, () -> registarComunic(orcId));

		menu.run();
	}

	private void adicionarPasso(String orcId) {
		System.out.println("Insira nome do passo a adicionar: ");
		String nome = scin.nextLine();
		System.out.println("Insira o material a adicionar: ");
		String material = scin.nextLine();
		System.out.println("Insira quantidade de material: ");
		int quantidade = scin.nextInt();
		System.out.println("Insira custo total do material: ");
		double custo = scin.nextDouble();
		System.out.println("Insira o tempo estimado: ");
		int tempo = scin.nextInt();
		model.criarPasso(orcId, nome, material, tempo, quantidade, custo);
	}

	private void gerarOrc(String orcId) {
		//print do orcamento
		try {
			model.alterarEstadoOrc(orcId, OrcamentoEstado.enviado);
		} catch (EstadoOrcNaoEValidoException e) {
			e.printStackTrace();
		}
	}

	private void registarComunic(String orcId) {
		System.out.println("Comentário sobre a comunicação: ");
		String comentario = scin.nextLine();
		model.comunicarErro(orcId, tecId, comentario);
	}

	private void realizarReparacao() {
		String repId = null;
		Menu menu = new Menu(new String[]{
				"Realizar passo da reparação",
				"Registar comunicação com o cliente",
				"Cancelar reparação"
		});

		// Registar os handlers das transições
		menu.setHandler(1, () -> realizarPasso(repId));
		menu.setHandler(2, () -> comunicarErro(repId));
		menu.setHandler(3, () -> cancelarRep(repId));

		menu.run();
	}

	private void realizarPasso(String repId) {
		System.out.println("Insira custo efetivo: ");
		double custo = scin.nextDouble();
		System.out.println("Insira o tempo gasto: ");
		int tempo = scin.nextInt();
		model.registaPasso(repId, tempo, custo);
	}

	private void comunicarErro(String repId) {
		System.out.println("Comentário sobre a comunicação: ");
		String msg = scin.nextLine();
		model.registaContacto(repId, tecId, msg);
	}

	private void cancelarRep(String repId) {
		System.out.println("Insira as horas gastas: ");
		int tempo = scin.nextInt();
		model.alterarEstadoRep(repId, ReparacaoEstado.cancelada);
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
		menu.setHandler(2, this::pedidoOrcamento);
		menu.setHandler(3, this::confirmarOrcamento);
		menu.setHandler(4, this::pedidoRepXpresso);
		menu.setHandler(5, this::registarEntregaEquip);

		menu.run();
	}

	private void registaCliente() {
		try {
			System.out.println("Insira o nif do cliente: ");
			String nif = scin.nextLine();
			System.out.println("Insira o numero do cliente: ");
			String numero = scin.nextLine();
			System.out.println("Insira o email do cliente: ");
			String email = scin.nextLine();
			model.registaCliente(nif, numero, email);
			System.out.println("Cliente registado!");
		} catch (ClienteJaExisteException e) {
			e.printStackTrace();
		}
	}

	private void pedidoOrcamento() {
		try {
			System.out.println("Insira o nif do cliente: ");
			String nif = scin.nextLine();
			System.out.println("Insira o identificador do equipamento: ");
			String eqId = scin.nextLine();
			System.out.println("Insira a descrição do orçamento: ");
			String desc = scin.nextLine();
			model.registarOrcamento(nif, eqId, desc);
			System.out.println("Pedido de Orcamento efetuado");
		} catch (EquipamentoNaoExisteException e) {
			e.printStackTrace();
		}
	}

	private void confirmarOrcamento() {
		Menu menu = new Menu(new String[]{
				"Confirmar Orcamento",
				"Recusar Orcamento"
		});

		menu.setHandler(1, this::confirmaOrc);
		menu.setHandler(2, this::recusaOrc);

		menu.runOnce();
	}

	private void confirmaOrc() {
		try {
			System.out.println("Insira o identificador do Orcamento: ");
			String id = scin.nextLine();
			model.alterarEstadoOrc(id, OrcamentoEstado.aceite);
			//criar rep?
		} catch (EstadoOrcNaoEValidoException e) {
			e.printStackTrace();
		}
	}

	private void recusaOrc() {
		try {
			System.out.println("Insira o identificador do Orcamento: ");
			String id = scin.nextLine();
			model.alterarEstadoOrc(id, OrcamentoEstado.arquivado);
		} catch (EstadoOrcNaoEValidoException e) {
			e.printStackTrace();
		}
	}

	private void pedidoRepXpresso() {
		try {
			System.out.println("Insira o tipo de reparação expresso: ");
			String tipo = scin.nextLine();
			System.out.println("Insira o nif do cliente: ");
			String desc = scin.nextLine();
			System.out.println("Insira o identificador do equipamento: ");
			String eqId = scin.nextLine();
			model.addRepExpresso(eqId, tipo);
		} catch (EquipamentoNaoExisteException e) {
			e.printStackTrace();
		}
	}

	private void registarEntregaEquip() {
		try {
			System.out.println("Insira o identificador do equipamento: ");
			String eqId = scin.nextLine();
			System.out.println(("Insira identificador da reparação"));
			String repId = scin.nextLine();
			model.alteraEstadoEq(eqId, EstadoEquipamento.entregue);
			model.alterarEstadoRep(repId, ReparacaoEstado.pago);
		} catch (EquipamentoNaoExisteException e) {
			e.printStackTrace();
		}
	}

}