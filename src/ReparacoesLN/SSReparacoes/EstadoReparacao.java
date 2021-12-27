package ReparacoesLN.SSReparacoes;

import java.util.Date;

public enum EstadoReparacao {
	reparado,
	aguadaReparacao,
	emReparacao,
	cancelada,
	pago,
	recusaPagar,
	prontoLevantar;

	private Date data;
	private String comentario;

	/**
	 * 
	 * @param msg
	 */
	public void setComentario(String msg) {
		this.comentario = msg;
	}

}