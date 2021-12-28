package ReparacoesLN.SSReparacoes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

import ReparacoesLN.SSColaboradores.*;
import ReparacoesLN.SSClientes.*;
import b.C.S;

public class GestReparacoesFacade implements IGestReparacoes {

	private Map<String, Reparacao> reps;
	private Map<String, Orcamento> orcs;
	private List<ReparacaoExpresso> reparacoesDisponiveis;

	public List<Orcamento> getOrcamentosAtivos() {
		return this.orcs.values().stream().filter(Orcamento::estaAtivo).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param ref
	 */
	public Orcamento getOrcamento(String ref) {
		// TODO - implement GestReparacoesFacade.getOrcamento
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param id
	 */
	public Reparacao getReparacao(String id) {
		// TODO - implement GestReparacoesFacade.getReparacao
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 */
	public List<Orcamento> filterOrcamentos(Predicate<Orcamento> p) {
		// TODO - implement GestReparacoesFacade.filterOrcamentos
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param orcID
	 * @param estado
	 */
	public void alterarEstadoOrc(String orcID, EstadoOrcamento estado) {
		Orcamento o = this.orcs.get(orcID);
		o.alteraEstado(estado);
	}

	/**
	 * 
	 * @param repID
	 * @param estado
	 */
	public void alterarEstadoRep(String repID, EstadoReparacao estado) {
		Reparacao r = this.reps.get(repID);
		r.alteraEstado(estado, null);
	}

	/**
	 * 
	 * @param repID
	 * @param estado
	 * @param comentario
	 */
	public void alterarEstadoRep(String repID, EstadoReparacao estado, String comentario) {
		Reparacao r = this.reps.get(repID);
		r.alteraEstado(estado, comentario);
	}

	/**
	 * 
	 * @param nomeRepXpresso
	 */
	public Integer getTempoEstimado(String nomeRepXpresso) {
		// TODO - implement GestReparacoesFacade.getTempoEstimado
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nomePasso
	 */
	public Boolean existePasso(String nomePasso) {
		// TODO - implement GestReparacoesFacade.existePasso
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nomeCategoria
	 */
	public Boolean existeCategoria(int nomeCategoria) {
		// TODO - implement GestReparacoesFacade.existeCategoria
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nome
	 */
	public Boolean existeRepXpresso(String nome) {
		// TODO - implement GestReparacoesFacade.existeRepXpresso
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nomeCategoria
	 */
	public void registaCategoria(String nomeCategoria) {
		// TODO - implement GestReparacoesFacade.registaCategoria
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param repId
	 * @param msg
	 * @param tec
	 */
	public void registaContacto(String repId, String msg, Tecnico tec) {
		Comunicacao c = new Comunicacao(tec, LocalDateTime.now(), msg);
		Reparacao r = getReparacao(repId);
		r.addComunicacao(c);
	}

	/**
	 * 
	 * @param repID
	 * @param mins
	 * @param custo
	 */
	public void registaPasso(String repID, Integer mins, Double custo) {
		// TODO - implement GestReparacoesFacade.registaPasso
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nome
	 * @param desc
	 * @param preco
	 * @param tempo
	 */
	public void registaRepXpresso(String nome, String desc, Double preco, Integer tempo) {
		// TODO - implement GestReparacoesFacade.registaRepXpresso
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param equip
	 * @param descr
	 */
	public void registarOrcamento(Equipamento equip, String descr) {
		Orcamento o = new Orcamento(equip, descr);
		this.orcs.put(o.getID(), o);
	}

	/**
	 * 
	 * @param orcId
	 * @param passos
	 */
	public void registaPT(String orcId, List<PassoReparacao> passos) {
		Orcamento o = this.orcs.get(orcId);
		o.setPT(passos);
	}

	/**
	 * 
	 * @param equip
	 * @param nomeRepXpresso
	 * @param tec
	 */
	public void addRepExpresso(Equipamento equip, String nomeRepXpresso, Tecnico tec) {
		// TODO - implement GestReparacoesFacade.addRepExpresso
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param orc
	 */
	public void addReparacao(Orcamento orc) {
		// TODO - implement GestReparacoesFacade.addReparacao
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param orc
	 */
	public Reparacao criarReparacao(Orcamento orc) {
		// TODO - implement GestReparacoesFacade.criarReparacao
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param repID
	 */
	public CustoTotalReparacao calcularPrecoRep(String repID) {
		// TODO - implement GestReparacoesFacade.calcularPrecoRep
		throw new UnsupportedOperationException();
	}

	public Reparacao pedidoRepMaisUrg() {
		// TODO - implement GestReparacoesFacade.pedidoRepMaisUrg
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param orcId
	 */
	public void generateOrc(String orcId) {
		// TODO - implement GestReparacoesFacade.generateOrc
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param orcId
	 */
	public void enviarOrcamento(String orcId) {
		// TODO - implement GestReparacoesFacade.enviarOrcamento
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param orc
	 * @param tec
	 * @param msg
	 */
	public void comunicarErro(Orcamento orc, Tecnico tec, String msg) {
		Comunicacao c = new Comunicacao(tec, LocalDateTime.now(), msg);
		orc.addComunicacao(c);
	}

	/**
	 * 
	 * @param msg
	 * @param dest
	 */
	public void enviarEmail(String msg, String dest) {
		// TODO - implement GestReparacoesFacade.enviarEmail
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param orcID
	 * @param nomePasso
	 * @param mat
	 * @param tempo
	 */
	public void criarPasso(String orcID, String nomePasso, Material mat, Integer tempo) {
		// TODO - implement GestReparacoesFacade.criarPasso
		throw new UnsupportedOperationException();
	}

	public void arquivarOrcamentos() {
		// TODO - implement GestReparacoesFacade.arquivarOrcamentos
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param data
	 * @return
	 */
	public Map<Tecnico, ReparacoesPorMes> getReparacoesMes(LocalDateTime data) {
		// TODO - implement GestReparacoesFacade.getReparacoesMes
		throw new UnsupportedOperationException();
	}

}