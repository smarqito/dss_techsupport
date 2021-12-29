package g31.ReparacoesLN.SSColaboradores.Agenda;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import g31.Middleware.EntradaNaoExisteException;
import g31.Middleware.NaoExisteDisponibilidadeException;

public class AgendaPorDia implements Comparable<AgendaPorDia>, Serializable {

	private TreeSet<EntradaAgenda> tarefas;
	/**
	 * Considera-se 8 horas de trabalhos
	 * Tempo em minutos
	 */
	private Integer tempoDisp = 8 * 60;
	private LocalDate data;

	/**
	 * 
	 * @param data
	 */
	public AgendaPorDia(LocalDate data) {
		tarefas = new TreeSet<>();
		this.data = data;
	}

	public AgendaPorDia(AgendaPorDia apd) {
		tarefas = apd.tarefas.stream().collect(Collectors.toCollection(() -> new TreeSet<>()));
		tempoDisp = apd.tempoDisp;
		data = apd.data;
	}

	public LocalDate getData() {
		return data;
	}

	/**
	 * 
	 * @param t
	 * @throws NaoExisteDisponibilidadeException
	 */
	public LocalTime temDisponibilidade(Integer t) throws NaoExisteDisponibilidadeException {
		if (tempoDisp >= t) {
			LocalTime now = LocalTime.now();
			if (now.compareTo(LocalTime.of(18, 30, 0)) >= 0 && this.data.compareTo(LocalDate.now()) <= 0) {
				throw new NaoExisteDisponibilidadeException();
			}
			if (tarefas.size() == 0) {
				if (this.data.compareTo(LocalDate.now()) > 0) {
					return LocalTime.of(9, 30, 0);
				}
				if (now.compareTo(LocalTime.of(18, 30, 0)) <= 0) {
					return LocalTime.of(Math.min(18, now.getHour()), Math.max(30, now.getMinute()), 0);
				}
			} else {
				LocalTime prox = tarefas.last().fim();
				if (prox.compareTo(LocalTime.of(18, 30, 0)) <= 0) {
					return LocalTime.of(Math.min(18, prox.getHour()), Math.max(30, prox.getMinute()), 0);
				}
			}
		}
		throw new NaoExisteDisponibilidadeException();
	}

	/**
	 * Adiciona o evento e retorna o fim do mesmo
	 * 
	 * @param duracao Duracao do evento
	 * @param det     Detalhes do evento
	 * @return Fim do evento
	 * @throws NaoExisteDisponibilidadeException
	 */
	public LocalDateTime addEvento(Integer duracao, String det) throws NaoExisteDisponibilidadeException {
		LocalTime inicio = temDisponibilidade(duracao);
		EntradaAgenda ea = new EntradaAgenda(inicio, duracao, det);
		tarefas.add(ea);
		LocalTime fim = ea.fim();
		tempoDisp -= duracao;
		return LocalDateTime.of(this.data, fim);
	}

	/**
	 * 
	 * @param data
	 * @throws EntradaNaoExisteException
	 */
	public void removeEvento(LocalDateTime data) throws EntradaNaoExisteException {
		EntradaAgenda ea = getEntradaAgenda(data.toLocalTime());
		tarefas.remove(ea);
	}

	public Integer getTempoDisponivel() {
		return this.tempoDisp;
	}

	/**
	 * 
	 * @param time
	 * @throws EntradaNaoExisteException
	 */
	public EntradaAgenda getEntradaAgenda(LocalTime time) throws EntradaNaoExisteException {
		try {
			return tarefas.stream().filter(x -> x.getInicio().compareTo(time) == 0).findFirst().get();
		} catch (NoSuchElementException e) {
			throw new EntradaNaoExisteException();
		}
	}

	public Set<EntradaAgenda> getEntradaAgenda() {
		return tarefas.stream().collect(Collectors.toCollection(() -> new TreeSet<>()));
	}

	public AgendaPorDia clone() {
		return new AgendaPorDia(this);
	}

	@Override
	public int compareTo(AgendaPorDia arg0) {
		return this.data.compareTo(arg0.data);
	}

}