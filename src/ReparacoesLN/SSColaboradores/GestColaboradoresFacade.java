package ReparacoesLN.SSColaboradores;

import java.util.*;
import ReparacoesLN.SSClientes.*;

public class GestColaboradoresFacade implements IGestColaboradores {

	private Colaborador colabs;
	private List<Balcao> balcao;
	private GestAgenda agenda;

	public void registaColaborador() {
		// TODO - implement GestColaboradoresFacade.registaColaborador
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tipo
	 * @param de
	 * @param ate
	 */
	public Map<Funcionario, List<Equipamento>> getEquipFuncBalcao(Class tipo, DateTime de, DateTime ate) {
		// TODO - implement GestColaboradoresFacade.getEquipFuncBalcao
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param de
	 * @param ate
	 */
	public Map<Funcionario, List<Equipamento>> getEquipRecebidos(DateTime de, DateTime ate) {
		// TODO - implement GestColaboradoresFacade.getEquipRecebidos
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param de
	 * @param ate
	 */
	public Map<Funcionario, List<Equipamento>> getEquipEntregue(DateTime de, DateTime ate) {
		// TODO - implement GestColaboradoresFacade.getEquipEntregue
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tecID
	 */
	public Tecnico getTecnico(String tecID) {
		// TODO - implement GestColaboradoresFacade.getTecnico
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param cod
	 */
	public Boolean validaIdentificacao(String cod) {
		// TODO - implement GestColaboradoresFacade.validaIdentificacao
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param duracao
	 */
	public String existeDisponibilidade(Integer duracao) {
		// TODO - implement GestColaboradoresFacade.existeDisponibilidade
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tecId
	 * @param tempo
	 * @param detalhes
	 */
	public DateTime addEventoAgenda(String tecId, Integer tempo, String detalhes) {
		// TODO - implement GestColaboradoresFacade.addEventoAgenda
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tempo
	 * @param detalhes
	 */
	public DateTime addEventoAgenda(Integer tempo, String detalhes) {
		// TODO - implement GestColaboradoresFacade.addEventoAgenda
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tecId
	 * @param data
	 */
	public void removeEventoAgenda(String tecId, DateTime data) {
		// TODO - implement GestColaboradoresFacade.removeEventoAgenda
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param duracao
	 */
	public TecData prazoReparacaoMaisProx(Integer duracao) {
		// TODO - implement GestColaboradoresFacade.prazoReparacaoMaisProx
		throw new UnsupportedOperationException();
	}

}