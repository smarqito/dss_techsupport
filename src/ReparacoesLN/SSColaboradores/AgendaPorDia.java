package ReparacoesLN.SSColaboradores;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import Middleware.EntradaNaoExisteException;
import Middleware.NaoExisteDisponibilidadeException;

public class AgendaPorDia implements Comparator<AgendaPorDia>, Serializable {

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
			return tarefas.last().fim();
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

	@Override
	public int compare(AgendaPorDia arg0, AgendaPorDia arg1) {
		return arg0.data.compareTo(arg1.data);
	}

	public AgendaPorDia clone() {
		return new AgendaPorDia(this);
	}

}