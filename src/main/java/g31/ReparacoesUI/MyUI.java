package g31.ReparacoesUI;

import g31.Middleware.*;
import g31.ReparacoesLN.IReparacoesLN;
import g31.ReparacoesLN.SSClientes.EstadoEquipamento;
import g31.ReparacoesLN.SSColaboradores.*;
import g31.ReparacoesLN.SSReparacoes.OrcamentoEstado;
import g31.ReparacoesLN.SSReparacoes.ReparacaoEstado;
import g31.ReparacoesLN.SSReparacoes.ReparacaoProgramada;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MyUI {

	public static IReparacoesLN model;
	public static Scanner scin;
	private MenuTecnico mt;
	private MenuGestor mg;
	private MenuFuncBalcao mf;

	/**
	 * Construtor.
	 * <p>
	 * Cria os menus e a camada de negócio.
	 */
	public MyUI(IReparacoesLN model) {
		this.model = model;		
		scin = new Scanner(System.in);
		mt = new MenuTecnico();
		mg = new MenuGestor();
		mf = new MenuFuncBalcao();

	}

	public void run() {
		System.out.println("Bem vindo ao Sistema DSS-TechSupport!");
		this.menuPrincipal();
		System.out.println("Até breve...");
	}

	private void menuPrincipal() {
		Menu menu = new Menu(new String[] {
				"Autenticar"
		});

		// mais pré-condições?

		// Registar os handlers das transições
		menu.setHandler(1, this::autenticarColaborador);

		// Executar o menu
		menu.run();
		// System.out.println("Guardando...");
		// this.model.save();

	}

	private void autenticarColaborador() {
		System.out.println("Insira o seu número de identificação: ");
		String id = scin.nextLine();
		try {
			if (this.model.existeColaborador(id)) {
				System.out.println("Acesso garantido");
				switch (this.model.getColaborador(id).getClass().getSimpleName()) {
					case "Gestor":
						mg.menuGestor();
						break;
					case "Tecnico":
						mt.menuTecnico(id);
						break;
					case "FuncionarioBalcao":
						mf.menuFuncionarioBalcao();
						break;
				}
			} else {
				System.out.println("Acesso Recusado");
			}
		} catch (ColaboradorNaoExisteException e) {
			e.printStackTrace();
		}
	}
}