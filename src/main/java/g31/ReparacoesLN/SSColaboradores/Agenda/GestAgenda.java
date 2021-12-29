package g31.ReparacoesLN.SSColaboradores.Agenda;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import g31.Middleware.EntradaNaoExisteException;
import g31.Middleware.NaoExisteDisponibilidadeException;
import g31.Middleware.TecnicoJaTemAgendaException;
import g31.Middleware.TecnicoNaoTemAgendaException;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.Tecnico;

public class GestAgenda implements Serializable {

	private Deque<Agenda> agendas;

	public GestAgenda() {
		this.agendas = new ArrayDeque<>();
	}

	public Agenda getAgenda(String tecId) throws TecnicoNaoTemAgendaException {
		try {
			return this.agendas.stream().filter(x -> Objects.equals(x.getTecnicoId(), tecId)).findFirst().get();
		} catch (NoSuchElementException e) {
			throw new TecnicoNaoTemAgendaException(tecId);
		}
	}

	public AgendaPorDia getAgendaDia(LocalDate data, String tecId) throws TecnicoNaoTemAgendaException {
		Agenda ag = getAgenda(tecId);
		return ag.getAgendaDia(data).clone();
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
	 * @throws TecnicoNaoTemAgendaException
	 */
	public LocalDateTime addEvento(String tecId, Integer tempo, String detalhes)
			throws NaoExisteDisponibilidadeException, TecnicoNaoTemAgendaException {
		return getAgenda(tecId).addEvento(tempo, detalhes);
	}

	/**
	 * 
	 * @param tempo
	 * @param detalhes
	 * @throws NaoExisteDisponibilidadeException
	 * @throws TecnicoNaoTemAgendaException
	 */
	public LocalDateTime addEvento(Integer tempo, String detalhes)
			throws NaoExisteDisponibilidadeException, TecnicoNaoTemAgendaException {
		TecData maisProx = prazoMaisProx(tempo);
		Agenda ag = getAgenda(maisProx.tecID);
		return ag.addEvento(tempo, detalhes);
	}

	/**
	 * 
	 * @param duracao
	 * @throws NaoExisteDisponibilidadeException
	 */
	public TecData prazoMaisProx(Integer duracao) throws NaoExisteDisponibilidadeException {
		Iterator<Agenda> it = agendas.iterator();
		LocalDateTime menor = LocalDateTime.MAX;
		String tec = null;
		while (it.hasNext()) {
			Agenda ag = it.next();
			TecData time = ag.prazoMaisProx(duracao);
			if (time.data.compareTo(menor) < 0) {
				menor = time.data;
				tec = time.tecID;
			}
		}
		if(tec == null) {
			throw new NaoExisteDisponibilidadeException("Nao ha tecnicos inseridos!!");
		}
		return new TecData(tec, menor);
	}

	/**
	 * 
	 * @param tecId
	 * @param data
	 * @throws EntradaNaoExisteException
	 * @throws TecnicoNaoTemAgendaException
	 */
	public void removeEvento(String tecId, LocalDateTime data)
			throws EntradaNaoExisteException, TecnicoNaoTemAgendaException {
		getAgenda(tecId).removeEvento(data);
	}

	/**
	 * 
	 * @param tec
	 * @throws TecnicoJaTemAgendaException
	 */
	public void addAgenda(Tecnico tec) throws TecnicoJaTemAgendaException {
		if (!agendas.stream().noneMatch(x -> x.getTecnicoId().equals(tec.getId()))) {
			throw new TecnicoJaTemAgendaException();
		}
		agendas.add(new Agenda(tec));

	}

}