package ReparacoesLN;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import Middleware.EquipaNaoExisteException;
import Middleware.OrcamentoNaoExisteException;
import ReparacoesLN.SSClientes.*;
import ReparacoesLN.SSReparacoes.*;
import ReparacoesLN.SSColaboradores.*;

public class ReparacoesLNFacade implements IReparacoesLN {

	private IGestClientes gestClientes;
	private IGestReparacoes gestReparacoes;
	private IGestColaboradores gestColaboradores;

	public static ReparacoesLNFacade getInstance() {
		// TODO - implement ReparacoesLNFacade.getInstance
		throw new UnsupportedOperationException();
	}

	/**
	 * Método que adiciona ás reparações por realizar uma reparação expresso nova
	 * Só acontece se houver disponibilidade e a reparação expresso pedida corresponde
	 * a um tipo existente na pool de reparações expresso válidas
	 * 
	 * Este método utiliza outro do GestReparacoesFacade para realizar a adição.
	 * 
	 * @param equipId Identificador do equipamento a adicionar 
	 * @param nomeRepExp Nome da reparação a realizar
	 */
	public void addRepExpresso(String equipId, String nomeRepExp) throws EquipaNaoExisteException {
		
		Boolean existeRepX = gestReparacoes.existeRepXpresso(nomeRepExp);
		
		if(existeRepX) {

			Equipamento eq = gestClientes.getEquipamento(equipId);

			Integer duracao_estimada = gestReparacoes.getTempoEstimado(nomeRepExp);

			/** 
			 try {
 
				 String tecID = gestColaboradores.existeDisponibilidade(duracao_estimada);
				 Tecnico tecnico = gestColaboradores.getTecnico(tecID);
				 gestReparacoes.addRepExpresso(eq, nomeRepExp, tecnico);
				 
			 } catch (NaoExisteDisponibilidadeException e) {
				 
				 throw new NaoExisteDisponibilidadeException("Não existem técnicos disponíveis para efetuar "+nomeRepExp+" no equipamento "+equipId);
			 }
		 
		} else throw new ReparacaoXPressoNaoExisteException("A reparacao "+nomeRepExp+" não existe!"); 
			*/
		}
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
	public void alterarEstadoOrc(String orcID, OrcamentoEstado estado) throws OrcamentoNaoExisteException {
		this.gestReparacoes.alterarEstadoOrc(orcID, estado);
	}

	/**
	 * 
	 * @param repID
	 * @param estado
	 */
	public void alterarEstadoRep(String repID, ReparacaoEstado estado) {
		this.gestReparacoes.alterarEstadoRep(repID, estado);
	}

	/**
	 * 
	 * @param repID
	 * @param estado
	 * @param comentario
	 */
	public void alterarEstadoRep(String repID, ReparacaoEstado estado, String comentario) {
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
	 * Método que regista a realização de um passo de reparação
	 * 
	 * Consiste na chamada do método do GestReparacoesFacade com o mesmo nome
	 * 
	 * @param repID Reparação a realizar
	 * @param mins Tempo Efetivo gasto
	 * @param custo Custo efetivo gasto
	 */
	public void registaPasso(String repID, Integer mins, Double custo) {
		
		gestReparacoes.registaPasso(repID, mins, custo);
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
		// TODO - implement ReparacoesLNFacade.registaCliente
		throw new UnsupportedOperationException();
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
	public void alteraEstadoEq(String equiID, EstadoEquipamento state) throws EquipaNaoExisteException {
		this.gestClientes.alteraEstadoEq(equiID, state);
	}

	/**
	 * Método que cria um novo passo e insere esse passo no plano de trabalhos de um orçamento
	 * 
	 * Utiliza o método do GestReparacoesFacade com o mesmo nome
	 * 
	 * @param orcID Orçamento a realizar
	 * @param nomePasso Nome do Passo a criar
	 * @param mat Todos os materiais usados, separados por ","
	 * @param tempo Tempo estimado
	 * @param qMat Quantidade de material usado
	 * @param custoMat Custo total do material usado
	 */
	public void criarPasso(String orcID, String nomePasso, String mat, Integer tempo, Integer qMat, Double custoMat) throws OrcamentoNaoExisteException {
		
		Material newMat = new Material(null, mat, custoMat, qMat); 

		//newMat = new Material(mat, qMat, custoMat);
		
		gestReparacoes.criarPasso(orcID, nomePasso, newMat, tempo);

	}

	/**
	 * 
	 * @param nif
	 * @param equipId
	 * @param descr
	 */
	public void registarOrcamento(String nif, String equipId, String descr) throws EquipaNaoExisteException {
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

	public void registaColaborador(String nome, String tipo) {
		this.gestColaboradores.registaColaborador(nome, tipo);
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
	public void registaPT(String orcId, List<PassoReparacao> passos) throws OrcamentoNaoExisteException {
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
	public Map<Tecnico, List<ReparacoesPorMes>> getReparacoesMes(LocalDateTime data) {
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