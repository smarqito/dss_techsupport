package ReparacoesLN.SSReparacoes;

import java.time.LocalDateTime;

public class EstadoReparacao implements Comparable<EstadoReparacao>{

	private ReparacaoEstado estado;
	private LocalDateTime data;
	private String comentario;

	public LocalDateTime getData() {
		return data;
	}

	public ReparacaoEstado getEstado() {
		return this.estado;
	}

	public String getComentario() {
		return comentario;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public void setEstado(ReparacaoEstado estado) {
		this.estado = estado;
	}

	/**
	 * 
	 * @param msg
	 */
	public void setComentario(String msg) {
		this.comentario = msg;
	}

	/**
	 * 
	 * @param s
	 * @param msg
	 */
	public EstadoReparacao(ReparacaoEstado s, String msg) {
		this.estado = s;
		this.data = LocalDateTime.now();
		this.comentario = msg;
	}


	@Override
	public int compareTo(EstadoReparacao o) {
		return o.getData().compareTo(this.data);
	}
}