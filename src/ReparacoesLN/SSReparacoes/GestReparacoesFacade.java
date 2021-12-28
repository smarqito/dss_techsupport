
package ReparacoesLN.SSReparacoes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Middleware.ReparacaoNaoExisteException;
import ReparacoesLN.SSColaboradores.*;
import ReparacoesLN.SSClientes.*;

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
	 * @throws ReparacaoNaoExisteException
	 */
	public Reparacao getReparacao(String id) throws ReparacaoNaoExisteException {
		if (reps.containsKey(id)) {
			return reps.get(id);
		}
		throw new ReparacaoNaoExisteException("id");
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
	public void alterarEstadoOrc(String orcID, OrcamentoEstado estado) {
		Orcamento o = this.orcs.get(orcID);
		o.alteraEstado(estado);
	}

	/**
	 * 
	 * @param repID
	 * @param estado
	 */
	public void alterarEstadoRep(String repID, ReparacaoEstado estado) {
		Reparacao r = this.reps.get(repID);
		r.alteraEstado(estado, null);
	}

	/**
	 * 
	 * @param repID
	 * @param estado
	 * @param comentario
	 */
	public void alterarEstadoRep(String repID, ReparacaoEstado estado, String comentario) {
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
	 * @throws ReparacaoNaoExisteException
	 */
	public void registaContacto(String repId, String msg, Tecnico tec) throws ReparacaoNaoExisteException {
		Comunicacao c = new Comunicacao(tec, LocalDateTime.now(), msg);
		Reparacao r = getReparacao(repId);
		r.addComunicacao(c);
	}

	/**
	 * Método que regista a realização de um passo de reparação
	 * Atualiza os valores efetivos do passo atual a realizar-se
	 * Atualiza a lista de passos realizados
	 * Atualiza o próximo passo a ser realizado como atual
	 * 
	 * @param repID Identificador da reparação a realizar
	 * @param mins  Tempo efetivo da reparação
	 * @param custo Custo efetivo da reparação
	 */
	public void registaPasso(String repID, Integer mins, Double custo) {

		ReparacaoProgramada rep = (ReparacaoProgramada) reps.get(repID);

		if (rep != null) {

			rep.registaPassoRealizado(mins, custo);
		}
		// else throw new ReparacaoNaoExisteException("A reparação com ID "+repID+" não
		// existe!");
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
	 * Método que adiciona ás reparações por realizar uma reparação expresso nova
	 * A reparação só é efetuada se o tipo de reparação existir no sistema
	 * 
	 * @param equip          Equipamento a reparar
	 * @param nomeRepXpresso Nome da reparação a efetuar
	 * @param tec            Técnico a realizar a reparação
	 */
	public void addRepExpresso(Equipamento equip, String nomeRepXpresso, Tecnico tec) {

		ReparacaoExpresso repXpresso = reparacoesDisponiveis.stream().filter(x -> x.getNome().equals(nomeRepXpresso))
				.findAny().orElse(null);

		// if(repXpresso == null)
		// throw new ReparacaoXPressoNaoExisteException("A reparacao "+nomeRepExp+" não
		// existe!");

		Double preco = repXpresso.getPrecoFixo();
		Integer tempo = repXpresso.getTempoEstimado();

		// ReparacaoExpresso res = new ReparacaoExpresso(nomeRepXpresso, preco, tempo,
		// equip, tec);

		// String repXpressoID = res.getID();

		// reps.put(repXpressoID, res);
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
	 * Calcula o preco efetivo de uma reparacao. Apenas utiliza os passos que foram realizados
	 * Se a reparacao for interrompida a meio, apenas paga o relativo ao que foi feito!
	 * @param repID ID da reparacao a ser calculado
	 * @return Custo total Reparacao
	 * @throws ReparacaoNaoExisteException Caso a reparacao a ser calculado nao exista
	 */
	public CustoTotalReparacao calcularPrecoRep(String repID) throws ReparacaoNaoExisteException {
		Reparacao rep = this.getReparacao(repID);
		return rep.getPrecoEfetivo();

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
	 * Método que cria um novo passo e insere esse passo no plano de trabalhos de um
	 * orçamento
	 * 
	 * Esse passo não pode existir já no plano de trabalhos
	 * 
	 * @param orcID     Orçamento a realizar
	 * @param nomePasso Nome do passo a criar
	 * @param mat       Material usado no passo
	 * @param tempo     tempo estimado para o passo
	 */
	public void criarPasso(String orcID, String nomePasso, Material mat, Integer tempo) {

		Orcamento orc = orcs.get(orcID);

		// if(orc == null)
		// throw new OrcamentoNaoExisteException("O orçamento com identificador
		// "+orcID+" não existe!");

		Boolean existePasso = orc.existePasso(nomePasso);

		if (existePasso == false) {

			orc.addPasso(nomePasso, tempo, mat);

		}
		// else throw new PassoNaoValidoException("O passo "+nomePasso+" não é válido!")
		// ;
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