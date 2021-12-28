package ReparacoesLN.SSReparacoes;

import ReparacoesLN.SSClientes.*;

import java.time.LocalDateTime;
import java.util.*;
import ReparacoesLN.SSColaboradores.*;

public abstract class Reparacao {

	private Equipamento equipamento;
	private Set<EstadoReparacao> estados;
	private List<Comunicacao> comunicacoes;
	private Tecnico tecnico;
	private String id;
	private LocalDateTime prazoReparacao;
	private LocalDateTime dataCriacao;

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public Set<EstadoReparacao> getEstados() {
		return new HashSet<>(estados);
	}

	public List<Comunicacao> getComunicacoes() {
		return comunicacoes;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getPrazoReparacao() {
		return prazoReparacao;
	}

	public LocalDateTime getDataCriacao() {
		return this.dataCriacao;
	}

	/**
	 * 
	 * @param novoEstado
	 * @param msg
	 */


	public void alteraEstado(EstadoReparacao novoEstado, String msg) {
		novoEstado.setComentario(msg);
		this.estados.add(novoEstado);
	}

	public EstadoReparacao getUltimoEstado() {
		return this.estados.iterator().next();
	}

	public abstract CustoTotalReparacao getPrecoEfetivo();


	public void addComunicacao(Comunicacao c) {
		this.comunicacoes.add(c);
	}
}