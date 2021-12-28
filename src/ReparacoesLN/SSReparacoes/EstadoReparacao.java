package ReparacoesLN.SSReparacoes;

import java.time.LocalDateTime;

public class EstadoReparacao {

	private ReparacaoEstado estado;
	private LocalDateTime data;
	private String comentario;

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
		// TODO - implement EstadoReparacao.EstadoReparacao
		throw new UnsupportedOperationException();
	}

	public ReparacaoEstado getEstado() {
		return this.estado;
	}

}