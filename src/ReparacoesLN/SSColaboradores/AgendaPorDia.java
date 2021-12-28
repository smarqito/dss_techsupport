package ReparacoesLN.SSColaboradores;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class AgendaPorDia {

	private List<EntradaAgenda> tarefas;
	private Integer tempoDisp = 8*60;
	private LocalDate data;

	public LocalDate getData() {
		return data;
	}

	/**
	 * 
	 * @param data
	 */
	public AgendaPorDia(Date data) {
		// TODO - implement AgendaPorDia.AgendaPorDia
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param t
	 */
	public LocalTime temDisponibilidade(Integer t) {
		// TODO - implement AgendaPorDia.temDisponibilidade
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param duracao
	 * @param det
	 */
	public LocalDateTime addEvento(Integer duracao, String det) {
		// TODO - implement AgendaPorDia.addEvento
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param data
	 */
	public void removeEvento(LocalDateTime data) {
		// TODO - implement AgendaPorDia.removeEvento
		throw new UnsupportedOperationException();
	}

	public Integer getTempoDisponivel() {
		// TODO - implement AgendaPorDia.getTempoDisponivel
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param time
	 */
	public EntradaAgenda getEntradaAgenda(Time time) {
		// TODO - implement AgendaPorDia.getEntradaAgenda
		throw new UnsupportedOperationException();
	}

}