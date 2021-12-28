package ReparacoesLN.SSReparacoes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

import ReparacoesLN.SSColaboradores.*;
import ReparacoesLN.SSClientes.*;

public class GestReparacoesFacade implements IGestReparacoes {

	private Map<String, Reparacao> reps;
    private Map<String, Orcamento> orcs;
    private Set<ReparacaoExpresso> reparacoesDisponiveis;

	public List<Orcamento> getOrcamentosAtivos() {
		// TODO - implement GestReparacoesFacade.getOrcamentosAtivos
		throw new UnsupportedOperationException();
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
		// TODO - implement GestReparacoesFacade.alterarEstadoOrc
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param repID
	 * @param estado
	 */
	public void alterarEstadoRep(String repID, EstadoReparacao estado) {
		// TODO - implement GestReparacoesFacade.alterarEstadoRep
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param repID
	 * @param estado
	 * @param comentario
	 */
	public void alterarEstadoRep(String repID, EstadoReparacao estado, String comentario) {
		// TODO - implement GestReparacoesFacade.alterarEstadoRep
		throw new UnsupportedOperationException();
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
		// TODO - implement GestReparacoesFacade.registaContacto
		throw new UnsupportedOperationException();
	}

	/**
	 * Método que regista a realização de um passo de reparação
	 * Atualiza os valores efetivos do passo atual a realizar-se
	 * Atualiza a lista de passos realizados 
	 * Atualiza o próximo passo a ser realizado como atual
	 * 
	 * @param repID Identificador da reparação a realizar
	 * @param mins Tempo efetivo da reparação
	 * @param custo Custo efetivo da reparação
	 */
	public void registaPasso(String repID, Integer mins, Double custo) {
		
		ReparacaoProgramada rep = (ReparacaoProgramada) reps.get(repID);

		if(rep != null) {

			rep.registaPassoRealizado(mins, custo);
		} 
		//else throw new ReparacaoNaoExisteException("A reparação com ID "+repID+" não existe!");
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
		// TODO - implement GestReparacoesFacade.registarOrcamento
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param orcId
	 * @param passos
	 */
	public void registaPT(String orcId, List<PassoReparacao> passos) {
		// TODO - implement GestReparacoesFacade.registaPT
		throw new UnsupportedOperationException();
	}

	/**
	 * Método que adiciona ás reparações por realizar uma reparação expresso nova 
	 * A reparação só é efetuada se o tipo de reparação existir no sistema
	 * 
	 * @param equip Equipamento a reparar
	 * @param nomeRepXpresso Nome da reparação a efetuar
	 * @param tec Técnico a realizar a reparação
	 */
	public void addRepExpresso(Equipamento equip, String nomeRepXpresso, Tecnico tec) {
		
		ReparacaoExpresso repXpresso = reparacoesDisponiveis.stream().filter(x -> x.getNome().equals(nomeRepXpresso)).findAny().orElse(null);
	
		//if(repXpresso == null)
			//throw new ReparacaoXPressoNaoExisteException("A reparacao "+nomeRepExp+" não existe!");
		
		Double preco = repXpresso.getPrecoFixo();
		Integer tempo = repXpresso.getTempoEstimado();

		//ReparacaoExpresso res = new ReparacaoExpresso(nomeRepXpresso, preco, tempo, equip, tec);

		//String repXpressoID = res.getID();

		//reps.put(repXpressoID, res);
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
		// TODO - implement GestReparacoesFacade.comunicarErro
		throw new UnsupportedOperationException();
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