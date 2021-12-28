package ReparacoesLN.SSColaboradores;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class GestAgenda implements Serializable {

	private List<Agenda> agendas;

	public Agenda getAgenda(String tecId){
		return this.agendas.stream().filter(x -> Objects.equals(x.getTecnicoId(), tecId)).findFirst().orElse(null);
	}

	/**
	 * 
	 * @param duracao
	 */
	public String temDisponibilidade(Integer duracao) {
		// TODO - implement GestAgenda.temDisponibilidade
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tecId
	 * @param tempo
	 * @param detalhes
	 */
	public LocalDateTime addEvento(String tecId, Integer tempo, String detalhes) {
		return getAgenda(tecId).addEvento(tempo, detalhes);
	}

	/**
	 * 
	 * @param tempo
	 * @param detalhes
	 */
	public LocalDateTime addEvento(Integer tempo, String detalhes) {
		// TODO - implement GestAgenda.addEvento
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param duracao
	 */
	public TecData prazoMaisProx(Integer duracao) {
		// TODO - implement GestAgenda.prazoMaisProx
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tecId
	 * @param data
	 */
	public void removeEvento(String tecId, LocalDateTime data) {
		getAgenda(tecId).removeEvento(data);
	}

	/**
	 * 
	 * @param tec
	 */
	public void addAgenda(Tecnico tec) {
		// TODO - implement GestAgenda.addAgenda
		throw new UnsupportedOperationException();
	}

}