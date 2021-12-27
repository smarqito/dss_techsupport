package ReparacoesLN.SSReparacoes;

import ReparacoesLN.SSClientes.*;

import java.time.LocalDateTime;
import java.util.*;
import ReparacoesLN.SSColaboradores.*;

public abstract class Reparacao {

	private Equipamento equipamento;
	private List<EstadoReparacao> estados;
	private List<Comunicacao> comunicacoes;
	private Tecnico tecnico;
	private String id;
	private LocalDateTime prazoReparacao;
	private LocalDateTime dataCriacao;

	/**
	 * 
	 * @param novoEstado
	 * @param msg
	 */
	public Boolean alteraEstado(EstadoReparacao novoEstado, String msg) {
		// TODO - implement Reparacao.alteraEstado
		throw new UnsupportedOperationException();
	}

	public EstadoReparacao getUltimoEstado() {
		// TODO - implement Reparacao.getUltimoEstado
		throw new UnsupportedOperationException();
	}

	public CustoTotalReparacao getPrecoEfetivo() {
		// TODO - implement Reparacao.getPrecoEfetivo
		throw new UnsupportedOperationException();
	}

	public Date getDataCriacao() {
		// TODO - implement Reparacao.getDataCriacao
		throw new UnsupportedOperationException();
	}

}