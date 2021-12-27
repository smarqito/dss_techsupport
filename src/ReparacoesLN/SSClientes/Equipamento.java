package ReparacoesLN.SSClientes;

import java.util.*;
import ReparacoesLN.SSReparacoes.*;

public class Equipamento {

	private List<Reparacao> historicoReps;
	private List<Orcamento> historicoOrcs;
	private EstadoEquipamento estado;
	private Cliente proprietario;
	private String id;
	private String codRegisto;
	private String marca;

	public Cliente getProprietario() {
		return this.proprietario;
	}

	public FormaContacto getFormaContacto() {
		// TODO - implement Equipamento.getFormaContacto
		throw new UnsupportedOperationException();
	}

	public EstadoEquipamento getEstado() {
		// TODO - implement Equipamento.getEstado
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param nif
	 * @return
	 */
	public boolean isProprietario(String nif) {
		// TODO - implement Equipamento.isProprietario
		throw new UnsupportedOperationException();
	}

}