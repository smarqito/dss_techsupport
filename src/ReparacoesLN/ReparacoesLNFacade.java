package ReparacoesLN;

import ReparacoesLN.SSClientes.*;
import ReparacoesLN.SSReparacoes.*;
import ReparacoesLN.SSColaboradores.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ReparacoesLNFacade implements IReparacoesLN {

	private IGestClientes gestClientes;
	private IGestReparacoes gestReparacoes;
	private IGestColaboradores gestColaboradores;

	public static ReparacoesLNFacade getInstance() {
		// TODO - implement ReparacoesLNFacade.getInstance
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param equipId
	 * @param nomeRepExp
	 */
	public void addRepExpresso(String equipId, String nomeRepExp) {
		// TODO - implement ReparacoesLNFacade.addRepExpresso
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param orcId
	 */
	public void enviarOrcamento(String orcId) {
		// TODO - implement ReparacoesLNFacade.enviarOrcamento
		throw new UnsupportedOperationException();
	}

	public List<Orcamento> getOrcamentosAtivos() {
		// TODO - implement ReparacoesLNFacade.getOrcamentosAtivos
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param orcID
	 * @param estado
	 */
	public void alterarEstadoOrc(String orcID, EstadoOrcamento estado) {
		this.gestReparacoes.alterarEstadoOrc(orcID, estado);
	}

	/**
	 * 
	 * @param repID
	 * @param estado
	 */
	public void alterarEstadoRep(String repID, EstadoReparacao estado) {
		this.gestReparacoes.alterarEstadoRep(repID, estado);
	}

	/**
	 * 
	 * @param repID
	 * @param estado
	 * @param comentario
	 */
	public void alterarEstadoRep(String repID, EstadoReparacao estado, String comentario) {
		this.gestReparacoes.alterarEstadoRep(repID, estado, comentario);
	}

	/**
	 * 
	 * @param repID
	 */
	public CustoTotalReparacao calcularPrecoRep(String repID) {
		// TODO - implement ReparacoesLNFacade.calcularPrecoRep
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param orcId
	 */
	public void generateOrc(String orcId) {
		// TODO - implement ReparacoesLNFacade.generateOrc
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param repID
	 * @param mins
	 * @param custo
	 */
	public void registaPasso(String repID, Integer mins, Double custo) {
		// TODO - implement ReparacoesLNFacade.registaPasso
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nif
	 */
	public Cliente getCliente(String nif) {
		// TODO - implement ReparacoesLNFacade.getCliente
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param equipID
	 */
	public Equipamento getEquipamento(String equipID) {
		// TODO - implement ReparacoesLNFacade.getEquipamento
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nif
	 */
	public Boolean existeCliente(String nif) {
		// TODO - implement ReparacoesLNFacade.existeCliente
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param codR
	 * @param marca
	 */
	public Boolean existeEquipamento(String codR, String marca) {
		// TODO - implement ReparacoesLNFacade.existeEquipamento
		throw new UnsupportedOperationException();
	}

	public List<Equipamento> getEqProntoLevantar() {
		return this.gestClientes.getEqProntoLevantar();
	}

	/**
	 * 
	 * @param nif
	 * @param numero
	 * @param email
	 */
	public void registaCliente(String nif, String numero, String email) {

	}

	/**
	 * 
	 * @param codR
	 * @param marca
	 * @param nif
	 */
	public void registaEquipamento(String codR, String marca, String nif) {
		// TODO - implement ReparacoesLNFacade.registaEquipamento
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param equiID
	 * @param state
	 */
	public void alteraEstadoEq(String equiID, EstadoEquipamento state) {
		this.gestClientes.alteraEstadoEq(equiID, state);
	}

	/**
	 * 
	 * @param orcID
	 * @param nomePasso
	 * @param mat
	 * @param tempo
	 * @param qMat
	 * @param custoMat
	 */
	public void criarPasso(String orcID, String nomePasso, String mat, Integer tempo, Integer qMat, Double custoMat) {
		// TODO - implement ReparacoesLNFacade.criarPasso
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nif
	 * @param equipId
	 * @param descr
	 */
	public void registarOrcamento(String nif, String equipId, String descr) {
		Equipamento e = this.gestClientes.getEquipamento(equipId);
		if (e.isProprietario(nif)){
			this.gestReparacoes.registarOrcamento(e, descr);
		}
	}

	/**
	 * 
	 * @param repID
	 * @param tecID
	 * @param msg
	 */
	public void registaContacto(String repID, String tecID, String msg) {
		// TODO - implement ReparacoesLNFacade.registaContacto
		throw new UnsupportedOperationException();
	}

	public void registaColaborador(Class c) {
		this.gestColaboradores.registaColaborador(c);
	}

	/**
	 * 
	 * @param de
	 * @param ate
	 */
	public Map<FuncionarioBalcao, List<Equipamento>> getEquipRecebidos(LocalDateTime de, LocalDateTime ate) {
		// TODO - implement ReparacoesLNFacade.getEquipRecebidos
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param de
	 * @param ate
	 */
	public Map<FuncionarioBalcao, List<Equipamento>> getEquipEntregue(LocalDateTime de, LocalDateTime ate) {
		// TODO - implement ReparacoesLNFacade.getEquipEntregue
		throw new UnsupportedOperationException();
	}

	public void saveInstance() {
		// TODO - implement ReparacoesLNFacade.saveInstance
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param orcId
	 * @param passos
	 */
	public void registaPT(String orcId, List<PassoReparacao> passos) {
		this.gestReparacoes.registaPT(orcId, passos);
	}

	/**
	 * 
	 * @param orcId
	 * @param msg
	 * @param tecID
	 */
	public void comunicarErro(String orcId, String msg, String tecID) {
		// TODO - implement ReparacoesLNFacade.comunicarErro
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param data
	 */
	public Map<Tecnico, ReparacoesPorMes> getReparacoesMes(LocalDateTime data) {
		// TODO - implement ReparacoesLNFacade.getReparacoesMes
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nome
	 * @param tempo
	 * @param custo
	 */
	public void registarRepExpresso(String nome, Integer tempo, Double custo) {
		// TODO - implement ReparacoesLNFacade.registarRepExpresso
		throw new UnsupportedOperationException();
	}

}