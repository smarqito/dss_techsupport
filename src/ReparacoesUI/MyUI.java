package ReparacoesUI;

import Middleware.*;
import ReparacoesLN.*;
import ReparacoesLN.SSClientes.Cliente;
import ReparacoesLN.SSClientes.EstadoEquipamento;
import ReparacoesLN.SSReparacoes.OrcamentoEstado;
import ReparacoesLN.SSReparacoes.ReparacaoEstado;
import ReparacoesLN.SSReparacoes.ReparacaoProgramada;

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
		menu.setHandler(1, this::autenticarColaborador);

		// Executar o menu
		menu.run();
		//System.out.println("Guardando...");
		//this.model.save();

	}

	private void autenticarColaborador() {
		System.out.println("Insira o seu número de identificação: ");
		String id = scin.nextLine();
		if (this.model.existeColaborador(id)) {
			System.out.println("Acesso garantido");
			switch (this.model.getColaborador(id).getClass().getSimpleName()){
				case "Gestor":
					this.menuGestor();
					break;
				case "Tecnico":
					this.menuTecnico(id);
					break;
				case "FuncionarioBalcao":
					this.menuFuncionarioBalcao();
					break;
			}
		} else {
			System.out.println("Acesso Recusado");
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
			System.out.println("Função do Colaborador: \n" +
								"1 - Gestor\n 2 - Técnico\n 3 - Funcionario de Balcao");
			int tipo = scin.nextInt();
			switch (tipo) {
				case 1:
					this.model.registaColaborador(nome, "Gestor");
					break;
				case 2:
					this.model.registaColaborador(nome, "Tecnico");
					break;
				case 3:
					this.model.registaColaborador(nome, "FuncionarioBalcao");
			}
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
		System.out.println(model.getReparacoesMes(data));
	}

	private void listarResumidas() {
		System.out.println("Mês que pretende ser avaliado: ");
		int mes = scin.nextInt();
		System.out.println("Ano: ");
		int ano = scin.nextInt();
		LocalDateTime data = LocalDateTime.of(ano, mes, 1, 1, 1, 1, 1);
		System.out.println(model.getReparacoesMes(data));
	}

	private void listarFluxoFunc() {
		System.out.println("Data de inicio da avaliação: \n Ano :");
		int anoDe = scin.nextInt();
		System.out.println("Mes :");
		int mesDe = scin.nextInt();
		System.out.println("Dia :");
		int diaDe = scin.nextInt();
		LocalDateTime dataDe = LocalDateTime.of(anoDe, mesDe, diaDe, 1, 1, 1, 1);
		System.out.println("Data do fim da avaliação: \n Ano :");
		int anoAte = scin.nextInt();
		System.out.println("Mes :");
		int mesAte = scin.nextInt();
		System.out.println("Dia :");
		int diaAte = scin.nextInt();
		LocalDateTime dataAte = LocalDateTime.of(anoAte, mesAte, diaAte, 1, 1, 1, 1);
		System.out.println("Recebidos :\n" + model.getEquipRecebidos(dataDe, dataAte));
		System.out.println("Entregues :\n" + model.getEquipEntregue(dataDe, dataAte));

	}

	private void menuColabEspecial() {
		Menu menu = new Menu(new String[]{
				"Criar Reparação Expresso",
				"Criar Passo de Reparação"
		});

		menu.setPreCondition(2, () -> model.getOrcamentosAtivos().size() > 0);

		// Registar os handlers das transições
		menu.setHandler(1, this::criarRepXpresso);
		menu.setHandler(2, this::criarPassoRep);

		menu.runOnce();
	}

	private void criarRepXpresso() {
		System.out.println("Insira o nome da reparação expresso a adicionar: ");
		String nome = scin.nextLine();
		System.out.println("Insira o preço: ");
		double preco = scin.nextDouble();
		System.out.println("Insira o tempo estimado");
		int tempo = scin.nextInt();
		model.registarRepExpresso(nome, tempo, preco);
		System.out.println("Reparação expresso criada com sucesso!");
	}

	private void criarPassoRep() {
		System.out.println("Insira o identificador do orçamento a atualizar: ");
		String orcId = scin.nextLine();
		this.adicionarPasso(orcId);
	}

	private void menuTecnico(String tecId){
		Menu menu = new Menu(new String[]{
				"Fazer orçamento",
				"Realizar reparação",
				"Menu Colaborador Especializado"
		});

		// pré-condições
		menu.setPreCondition(1, () -> model.getOrcamentosAtivos().size() > 0);
		//agenda nao vazia

		// Registar os handlers das transições
		menu.setHandler(1, () -> fazerOrcamento(tecId));
		menu.setHandler(2, () -> realizarReparacao(tecId));
		menu.setHandler(3, this::menuColabEspecial);

		menu.run();
	}

	private void fazerOrcamento(String tecId) {

		String orcId = null; //model.getOrcamentoMaisAntigo();

		Menu menu = new Menu(new String[]{
				"Definir plano de trabalhos",
				"Gerar Orcamento"
		});

		// Registar os handlers das transições
		menu.setHandler(1, () -> definirPLano(orcId, tecId));
		menu.setHandler(2, () -> gerarOrc(orcId));

		menu.runOnce();
	}

	private void definirPLano(String orcId, String tecId) {
		//print do orcamento
		Menu menu = new Menu(new String[]{
				"Adicionar passo de reparação",
				"Registar comunicação com o cliente"
		});

		// pré-condições

		// Registar os handlers das transições
		menu.setHandler(1, () -> adicionarPasso(orcId));
		menu.setHandler(2, () -> registarComunic(orcId, tecId));

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

	private void registarComunic(String orcId, String tecId) {
		System.out.println("Comentário sobre a comunicação: ");
		String comentario = scin.nextLine();
		model.comunicarErro(orcId, tecId, comentario);
	}

	private void realizarReparacao(String tecId) {
		System.out.println("Insira o identificador da reparação: ");
		String repId = scin.nextLine();
		if (model.getReparacao(repId).getClass().equals(ReparacaoProgramada.getClass())){
			Menu menu = new Menu(new String[]{
					"Realizar passo da reparação",
					"Registar comunicação com o cliente",
					"Cancelar reparação"
			});

			
			// Registar os handlers das transições
			menu.setHandler(1, () -> realizarPasso(repId));
			menu.setHandler(2, () -> comunicarErro(repId, tecId));
			menu.setHandler(3, () -> cancelarRep(repId));

			menu.run();
		}else {
			Menu menu = new Menu(new String[]{
					"Registar conclusão"
			});

			menu.setHandler(1, () -> registarConclusao(repId));

		}
	}

	private void registarConclusao(String repId) {
		model.alterarEstadoRep(repId, ReparacaoEstado.reparado);
	}

	private void realizarPasso(String repId) {
		System.out.println("Insira custo efetivo: ");
		double custo = scin.nextDouble();
		System.out.println("Insira o tempo gasto: ");
		int tempo = scin.nextInt();
		model.registaPasso(repId, tempo, custo);
	}

	private void comunicarErro(String repId, String tecId) {
		try {
			System.out.println("Comentário sobre a comunicação: ");
			String msg = scin.nextLine();
			model.registaContacto(repId, tecId, msg);
		} catch (ReparacaoNaoExisteException e) {
			e.printStackTrace();
		}
	}

	private void cancelarRep(String repId) {
		System.out.println("Insira as horas gastas: ");
		int tempo = scin.nextInt();
		model.alterarEstadoRep(repId, ReparacaoEstado.cancelada, "tempo gasto: " + tempo);
	}

	private void menuFuncionarioBalcao(){
		Menu menu = new Menu(new String[]{
				"Registar cliente",
				"Registar Equipamento",
				"Registar pedido de orçamento",
				"Confirmar orçamento",
				"Registar pedido de Reparação Expresso",
				"Registar entrega do equipamento"
		});

		// Registar os handlers das transições
		menu.setHandler(1, this::registaCliente);
		menu.setHandler(2, this::registaEquipamento);
		menu.setHandler(3, this::pedidoOrcamento);
		menu.setHandler(4, this::confirmarOrcamento);
		menu.setHandler(5, this::pedidoRepXpresso);
		menu.setHandler(6, this::registarEntregaEquip);

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

	private void registaEquipamento(){
		try {
			System.out.println("Insira o nif do cliente: ");
			String nif = scin.nextLine();
			System.out.println("Insira a marca do equipamento: ");
			String marca = scin.nextLine();
			System.out.println("Insira o código de registo do equipamento: ");
			String codR = scin.nextLine();
			model.registaEquipamento(codR, marca, nif);
		} catch (ClienteNaoExisteException | EquipamentoJaAssociadoException e) {
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
		System.out.println("Insira o identificador do Orcamento: ");
		String id = scin.nextLine();
		Menu menu = new Menu(new String[]{
				"Confirmar Orcamento",
				"Recusar Orcamento"
		});

		menu.setHandler(1, () -> confirmaOrc(id));
		menu.setHandler(2, () -> recusaOrc(id));

		menu.runOnce();
	}

	private void confirmaOrc(String id) {
		try {
			model.alterarEstadoOrc(id, OrcamentoEstado.aceite);
		} catch (EstadoOrcNaoEValidoException e) {
			e.printStackTrace();
		}
	}

	private void recusaOrc(String id) {
		try {
			model.alterarEstadoOrc(id, OrcamentoEstado.arquivado);
		} catch (EstadoOrcNaoEValidoException e) {
			e.printStackTrace();
		}
	}

	private void pedidoRepXpresso() {
		try {
			System.out.println("Insira o tipo de reparação expresso: ");
			String tipo = scin.nextLine();
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