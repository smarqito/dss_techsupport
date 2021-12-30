package g31.ReparacoesLN.SSReparacoes.Reparacao;

import g31.ReparacoesLN.SSClientes.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import g31.ReparacoesLN.SSColaboradores.Colaboradores.Tecnico;
import g31.ReparacoesLN.SSReparacoes.Comunicacao;

public abstract class Reparacao implements Serializable {

	private static int ID = 1;

	private static int GetID() {
		return ID++;
	}

	private Equipamento equipamento;
	private Set<EstadoReparacao> estados;
	private List<Comunicacao> comunicacoes;
	private Tecnico tecnico;
	private String id;
	private LocalDateTime prazoReparacao;
	private LocalDateTime dataCriacao;

	public Reparacao() {
		dataCriacao = LocalDateTime.now();
		comunicacoes = new ArrayList<>();
		estados = new TreeSet<>();
		alteraEstado(ReparacaoEstado.aguadaReparacao, "Inicio");
		id = GetID() + "";
	}

	public Reparacao(Equipamento equip, Tecnico tec) {
		this();
		this.equipamento = equip;
		this.tecnico = tec;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public Set<EstadoReparacao> getEstados() {
		return estados.stream().map(EstadoReparacao::clone).collect(Collectors.toSet());
	}

	public List<Comunicacao> getComunicacoes() {
		return comunicacoes.stream().map(Comunicacao::clone).collect(Collectors.toList());
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
		return dataCriacao;
	}

	/**
	 * 
	 * @param novoEstado
	 * @param msg
	 */

	public void alteraEstado(ReparacaoEstado novoEstado, String msg) {
		EstadoReparacao novo = new EstadoReparacao(novoEstado, msg);
		this.estados.add(novo);
	}

	public EstadoReparacao getUltimoEstado() {
		return this.estados.iterator().next();
	}

	public abstract CustoTotalReparacao getPrecoEfetivo();

	public void addComunicacao(Comunicacao c) {
		this.comunicacoes.add(c.clone());
	}

	public abstract ReparacaoEstado registaPassoRealizado(Integer tempo, Double custo);

	public abstract Reparacao clone();
}