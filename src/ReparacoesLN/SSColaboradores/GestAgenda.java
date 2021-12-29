package ReparacoesLN.SSColaboradores;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class GestAgenda implements Serializable {

	private Deque<Agenda> agendas;

	public Agenda getAgenda(String tecId){
		return this.agendas.stream().filter(x -> Objects.equals(x.getTecnicoId(), tecId)).findFirst().orElse(null);
	}

	/**
	 * 
	 * @param duracao
	 */
	public String temDisponibilidade(Integer duracao) {
		int total = agendas.size();
		String found = null;
		Agenda ag;
		while (total > 0 && found == null) {
			ag = agendas.element();
			String disp = ag.temDisponibilidade(duracao);
			if(disp != null) {
				found = disp;
			} else {
				total--;
				agendas.poll();
				agendas.add(ag);
			}
		}
		return found;
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