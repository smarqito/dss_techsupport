package ReparacoesLN.SSColaboradores;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.*;
import ReparacoesLN.SSClientes.*;

public class GestColaboradoresFacade implements IGestColaboradores {

	private Map<String, Colaborador> colabs;
	private List<Balcao> balcao;
	private GestAgenda agenda;
	private int id;

	public int getId() {
		return id++;
	}

	public void registaColaborador(Class c) {
		try {
			Colaborador colab = (Colaborador) c.getDeclaredConstructor().newInstance();
			this.colabs.put(colab.getId(), colab);
		} catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param tipo
	 * @param de
	 * @param ate
	 */
	public Map<FuncionarioBalcao, List<Equipamento>> getEquipFuncBalcao(Class tipo, LocalDateTime de, LocalDateTime ate) {
		// TODO - implement GestColaboradoresFacade.getEquipFuncBalcao
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param de
	 * @param ate
	 */
	public Map<FuncionarioBalcao, List<Equipamento>> getEquipRecebidos(LocalDateTime de, LocalDateTime ate) {
		// TODO - implement GestColaboradoresFacade.getEquipRecebidos
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param de
	 * @param ate
	 */
	public Map<FuncionarioBalcao, List<Equipamento>> getEquipEntregue(LocalDateTime de, LocalDateTime ate) {
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
	public LocalDateTime addEventoAgenda(String tecId, Integer tempo, String detalhes) {
		return this.agenda.addEvento(tecId, tempo, detalhes);
	}

	/**
	 * 
	 * @param tempo
	 * @param detalhes
	 */
	public LocalDateTime addEventoAgenda(Integer tempo, String detalhes) {
		// TODO - implement GestColaboradoresFacade.addEventoAgenda
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tecId
	 * @param data
	 */
	public void removeEventoAgenda(String tecId, LocalDateTime data) {
		agenda.removeEvento(tecId, data);
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