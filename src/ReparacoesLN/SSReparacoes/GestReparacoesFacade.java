
package ReparacoesLN.SSReparacoes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Middleware.EstadoOrcNaoEValidoException;
import Middleware.OrcamentoNaoExisteException;
import Middleware.ReparacaoExpressoJaExisteException;
import Middleware.ReparacaoNaoExisteException;
import ReparacoesLN.SSColaboradores.*;
import ReparacoesLN.SSClientes.*;

public class GestReparacoesFacade implements IGestReparacoes, Serializable {

	private Map<String, Reparacao> reps;
	private Map<String, Orcamento> orcs;
	private Set<Reparacao> repsPorData;
	private List<ReparacaoExpresso> reparacoesDisponiveis;

	public GestReparacoesFacade() {
		reps = new HashMap<>();
		orcs = new HashMap<>();
		repsPorData = new TreeSet<>();
		reparacoesDisponiveis = new ArrayList<>();
	}

	public List<Orcamento> getOrcamentosAtivos() {
		return this.orcs.values().stream().filter(Orcamento::estaAtivo).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param ref
	 * @throws OrcamentoNaoExisteException
	 */
	public Orcamento getOrcamento(String ref) throws OrcamentoNaoExisteException {
		if (orcs.containsKey(ref)) {
			return orcs.get(ref);
		}
		throw new OrcamentoNaoExisteException(ref);
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
		throw new ReparacaoNaoExisteException(id);
	}

	/**
	 * 
	 * @param p
	 */
	public List<Orcamento> filterOrcamentos(Predicate<Orcamento> p) {
		return orcs.values().stream().filter(p).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param orcID
	 * @param estado
	 * @throws EstadoOrcNaoEValidoException
	 */
	public void alterarEstadoOrc(String orcID, OrcamentoEstado estado) throws EstadoOrcNaoEValidoException {
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

	@Override
	public Integer getTempoEstimado(String nomeRepXpresso) throws ReparacaoNaoExisteException {
		if (existeRepXpresso(nomeRepXpresso)) {
			return reparacoesDisponiveis.stream().filter(x -> x.getNome().equals(nomeRepXpresso))
					.map(y -> y.getTempoEstimado()).findFirst().get();
		}
		throw new ReparacaoNaoExisteException(nomeRepXpresso);
	}

	@Override
	public Boolean existePasso(String nomePasso) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Boolean existeCategoria(int nomeCategoria) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ReparacaoExpresso getReparacaoExpresso(String nome) throws ReparacaoNaoExisteException {
		try {
			return this.reparacoesDisponiveis.stream().filter(x -> x.getNome().equals(nome)).findFirst().get().clone();
		} catch (NoSuchElementException e) {
			throw new ReparacaoNaoExisteException(nome);
		}
	}

	@Override
	public List<ReparacaoExpresso> getReparacaoExpresso() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean existeRepXpresso(String nome) {
		return reparacoesDisponiveis.stream().anyMatch(x -> x.getNome().equals(nome));
	}

	@Override
	public void registaCategoria(String nomeCategoria) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void registaContacto(String repId, String msg, Tecnico tec) throws ReparacaoNaoExisteException {
		Comunicacao c = new Comunicacao(tec, LocalDateTime.now(), msg);
		Reparacao r = getReparacao(repId);
		r.addComunicacao(c);
	}

	@Override
	public void registaPasso(String repID, Integer mins, Double custo) throws ReparacaoNaoExisteException {
		Reparacao rep = getReparacao(repID);
		rep.registaPassoRealizado(mins, custo);
	}

	@Override
	public void registaRepXpresso(String nome, Double preco, Integer tempo) throws ReparacaoExpressoJaExisteException {
		if (existeRepXpresso(nome)) {
			throw new ReparacaoExpressoJaExisteException("A reparação expresso " + nome + " já existe!");
		}
		ReparacaoExpresso repXpresso = new ReparacaoExpresso(preco, tempo, nome);
		reparacoesDisponiveis.add(repXpresso);
	}

	@Override
	public void registarOrcamento(Equipamento equip, String descr) {
		Orcamento o = new Orcamento(equip, descr);
		this.orcs.put(o.getID(), o);
	}

	@Override
	public void registaPT(String orcId, List<PassoReparacao> passos) {
		Orcamento o = this.orcs.get(orcId);
		o.setPT(passos);
	}

	@Override
	public void addRepExpresso(Equipamento equip, String nomeRepXpresso, Tecnico tec) throws ReparacaoNaoExisteException {

		if(!existeRepXpresso(nomeRepXpresso)){
			throw new ReparacaoNaoExisteException(nomeRepXpresso);
		}
		ReparacaoExpresso repXpresso = ;

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
	 * Calcula o preco efetivo de uma reparacao. Apenas utiliza os passos que foram
	 * realizados
	 * Se a reparacao for interrompida a meio, apenas paga o relativo ao que foi
	 * feito!
	 * 
	 * @param repID ID da reparacao a ser calculado
	 * @return Custo total Reparacao
	 * @throws ReparacaoNaoExisteException Caso a reparacao a ser calculado nao
	 *                                     exista
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