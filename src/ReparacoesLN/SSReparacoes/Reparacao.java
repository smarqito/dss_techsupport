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

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	/**
	 * 
	 * @param novoEstado
	 * @param msg
	 */


	public void alteraEstado(ReparacaoEstado novoEstado, String msg) {
		EstadoReparacao novo = new EstadoReparacao(novoEstado, msg);
		this.estados.add(0, novo);
	}

	public EstadoReparacao getUltimoEstado() {
		return this.estados.get(0);
	}

	public abstract CustoTotalReparacao getPrecoEfetivo();

	public LocalDateTime getDataCriacao() {
		return this.dataCriacao;
	}

	public void addComunicacao(Comunicacao c) {
		this.comunicacoes.add(c);
	}

	public abstract void registaPassoRealizado(Integer tempo, Double custo);
}