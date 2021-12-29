package ReparacoesLN.SSColaboradores;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import Middleware.EntradaNaoExisteException;
import Middleware.NaoExisteDisponibilidadeException;
import Middleware.TecnicoJaTemAgendaException;

public class GestAgenda implements Serializable {

	private Deque<Agenda> agendas;

	
	public GestAgenda() {
		this.agendas = new ArrayDeque<>();
	}

	public Agenda getAgenda(String tecId) {
		return this.agendas.stream().filter(x -> Objects.equals(x.getTecnicoId(), tecId)).findFirst().orElse(null);
	}

	/**
	 * 
	 * @param duracao
	 * @throws NaoExisteDisponibilidadeException
	 */
	public String temDisponibilidade(Integer duracao) throws NaoExisteDisponibilidadeException {
		int total = agendas.size();
		Agenda ag;
		while (total > 0) {
			ag = agendas.element();
			try {
				return ag.temDisponibilidade(duracao);
			} catch (NaoExisteDisponibilidadeException e) {
				total--;
				agendas.poll();
				agendas.add(ag);
			}
		}
		throw new NaoExisteDisponibilidadeException();
	}

	/**
	 * 
	 * @param tecId
	 * @param tempo
	 * @param detalhes
	 * @throws NaoExisteDisponibilidadeException
	 */
	public LocalDateTime addEvento(String tecId, Integer tempo, String detalhes) throws NaoExisteDisponibilidadeException {
		return getAgenda(tecId).addEvento(tempo, detalhes);
	}

	/**
	 * 
	 * @param tempo
	 * @param detalhes
	 * @throws NaoExisteDisponibilidadeException
	 */
	public LocalDateTime addEvento(Integer tempo, String detalhes) throws NaoExisteDisponibilidadeException {
		TecData maisProx = prazoMaisProx(tempo);
		Agenda ag = getAgenda(maisProx.tecID);
		return ag.addEvento(tempo, detalhes);
	}

	/**
	 * 
	 * @param duracao
	 */
	public TecData prazoMaisProx(Integer duracao) {
		Iterator<Agenda> it =  agendas.iterator();
		LocalDateTime menor = LocalDateTime.MAX;
		String tec = null;
		while(it.hasNext()) {
			Agenda ag = it.next();
			TecData time = ag.prazoMaisProx(duracao);
			if(time.data.compareTo(menor) < 0) {
				menor = time.data;
				tec = time.tecID;
			}
		}
		return new TecData(tec, menor);
	}

	/**
	 * 
	 * @param tecId
	 * @param data
	 * @throws EntradaNaoExisteException
	 */
	public void removeEvento(String tecId, LocalDateTime data) throws EntradaNaoExisteException {
		getAgenda(tecId).removeEvento(data);
	}

	/**
	 * 
	 * @param tec
	 * @throws TecnicoJaTemAgendaException
	 */
	public void addAgenda(Tecnico tec) throws TecnicoJaTemAgendaException {
		if(!agendas.stream().noneMatch(x -> x.getTecnicoId().equals(tec.getId()))) {
			throw new TecnicoJaTemAgendaException();
		}
		agendas.add(new Agenda(tec));
		
	}

}