package ReparacoesLN.SSColaboradores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import Middleware.EntradaNaoExisteException;
import Middleware.NaoExisteDisponibilidadeException;

public class Agenda {

	private List<AgendaPorDia> tarefasDia;
	private Tecnico tecnico;

	/**
	 * 
	 * @param t
	 * @throws NaoExisteDisponibilidadeException
	 */
	public String temDisponibilidade(Integer t) throws NaoExisteDisponibilidadeException {
		AgendaPorDia agPd = getAgendaDia(LocalDate.now());
		agPd.temDisponibilidade(t);
		return getTecnicoId();
	}

	/**
	 * Adiciona o evento no prazo mais proximo e retorna o fim do mesmo
	 * 
	 * @param tempo
	 * @param detalhes
	 * @return Data e hora do fim do evento (permite colocar como prazo de)
	 * @throws NaoExisteDisponibilidadeException
	 */
	public LocalDateTime addEvento(Integer tempo, String detalhes) throws NaoExisteDisponibilidadeException {
		TecData td = this.prazoMaisProx(tempo);
		return this.getAgendaDia(td.data.toLocalDate()).addEvento(tempo, detalhes);
	}

	/**
	 * 
	 * @param t
	 * @throws NaoExisteDisponibilidadeException
	 */
	public TecData prazoMaisProx(Integer t) {
		int days = 0; // talvez colocar um limite de dias?
		TecData td = null;
		while (true) {
			try {
				AgendaPorDia agPd = getAgendaDia(LocalDate.now().plusDays(days++));
				LocalTime time = agPd.temDisponibilidade(t);
				LocalDateTime data = LocalDateTime.of(agPd.getData(), time);
				td = new TecData(getTecnicoId(), data);
				return td;
			} catch (NaoExisteDisponibilidadeException e) {
				continue;
			}
		}
	}

	/**
	 * 
	 * @param data
	 * @throws EntradaNaoExisteException
	 */
	public void removeEvento(LocalDateTime data) throws EntradaNaoExisteException {
		this.getAgendaDia(data.toLocalDate()).removeEvento(data);
	}

	/**
	 * 
	 * @param data
	 */
	public AgendaPorDia getAgendaDia(LocalDate data) {
		try {
			return tarefasDia.stream().filter(x -> x.getData().equals(data)).findFirst().get();
		} catch (NoSuchElementException e) {
			AgendaPorDia agPd = new AgendaPorDia(data);
			tarefasDia.add(agPd);
			return agPd;
		}
	}

	public String getTecnicoId() {
		return tecnico.getId();
	}

}