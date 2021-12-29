package g31.ReparacoesLN.SSColaboradores.Agenda;

import java.io.Serializable;
import java.time.LocalTime;

public class EntradaAgenda implements Serializable, Comparable<EntradaAgenda> {

	private LocalTime inicio;
	private Integer duracao;

	private String detalhes;

	/**
	 * 
	 * @param inicio
	 * @param duracao
	 * @param det
	 */
	public EntradaAgenda(LocalTime inicio, Integer duracao, String det) {
		this.inicio = inicio;
		this.duracao = duracao;
		this.detalhes = det;
	}

	public LocalTime fim() {
		return inicio.plusMinutes(duracao);
	}

	public LocalTime getInicio() {
		return inicio;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public String getDetalhes() {
		return detalhes;
	}

	@Override
	public int compareTo(EntradaAgenda arg0) {
		return this.inicio.compareTo(arg0.inicio);
	}

}