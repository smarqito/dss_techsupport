package ReparacoesLN.SSReparacoes;

import ReparacoesLN.SSClientes.*;
import java.util.*;
import ReparacoesLN.SSColaboradores.*;

public class Orcamento {

	private Equipamento equipamento;
	private ReparacaoProgramada reparacao;
	private PlanoTrabalho plano;
	private List<EstadoOrcamento> estados;
	private List<Comunicacao> comunicacoes;
	private String id;
	private Date prazoReparacao;
	private Double preco;
	private Double custoHora;
	private String descrProb;

	/**
	 * 
	 * @param e
	 * @param descr
	 */
	public Orcamento(Equipamento e, String descr) {
		// TODO - implement Orcamento.Orcamento
		throw new UnsupportedOperationException();
	}

	public String getID() {
		// TODO - implement Orcamento.getID
		throw new UnsupportedOperationException();
	}

	public List<Material> getMaterial() {
		// TODO - implement Orcamento.getMaterial
		throw new UnsupportedOperationException();
	}

	public Date getPrazoRep() {
		// TODO - implement Orcamento.getPrazoRep
		throw new UnsupportedOperationException();
	}

	public PlanoTrabalho getPT() {
		// TODO - implement Orcamento.getPT
		throw new UnsupportedOperationException();
	}

	public FormaContacto getFormaContacto() {
		// TODO - implement Orcamento.getFormaContacto
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nome
	 * @param tempo
	 * @param material
	 */
	public void addPasso(String nome, Integer tempo, Material material) {
		// TODO - implement Orcamento.addPasso
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nomeP
	 * @param nomeSub
	 * @param tempo
	 * @param material
	 */
	public void addSubPasso(String nomeP, String nomeSub, Integer tempo, Material material) {
		// TODO - implement Orcamento.addSubPasso
		throw new UnsupportedOperationException();
	}

	public Boolean estaAtivo() {
		// TODO - implement Orcamento.estaAtivo
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param novoEstado
	 */
	public void alteraEstado(EstadoOrcamento novoEstado) {
		// TODO - implement Orcamento.alteraEstado
		throw new UnsupportedOperationException();
	}

	public String generateResume() {
		// TODO - implement Orcamento.generateResume
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nomePasso
	 */
	public Boolean existePasso(String nomePasso) {
		// TODO - implement Orcamento.existePasso
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nomePasso
	 */
	public PassoReparacao getPasso(String nomePasso) {
		// TODO - implement Orcamento.getPasso
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param data
	 * @param msg
	 * @param tec
	 */
	public void addComunicacao(DateTime data, String msg, Tecnico tec) {
		// TODO - implement Orcamento.addComunicacao
		throw new UnsupportedOperationException();
	}

	public Boolean passouPrazo() {
		// TODO - implement Orcamento.passouPrazo
		throw new UnsupportedOperationException();
	}

}