package ReparacoesLN.SSColaboradores;

import java.io.Serializable;
import java.time.LocalTime;

public class EntradaAgenda implements Serializable {

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

}