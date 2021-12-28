package ReparacoesLN;

import ReparacoesLN.SSReparacoes.*;
import ReparacoesLN.SSClientes.*;
import ReparacoesLN.SSColaboradores.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IReparacoesLN {

	/**
	 * 
	 * @param equipId
	 * @param nomeRepExp
	 */
	void addRepExpresso(String equipId, String nomeRepExp);

	/**
	 * 
	 * @param orcId
	 */
	void enviarOrcamento(String orcId);

	List<Orcamento> getOrcamentosAtivos();

	/**
	 * 
	 * @param orcID
	 * @param estado
	 */
	void alterarEstadoOrc(String orcID, EstadoOrcamento estado);

	/**
	 * 
	 * @param repID
	 * @param estado
	 */
	void alterarEstadoRep(String repID, EstadoReparacao estado);

	/**
	 * 
	 * @param repID
	 * @param estado
	 * @param comentario
	 */
	void alterarEstadoRep(String repID, EstadoReparacao estado, String comentario);

	/**
	 * 
	 * @param repID
	 */
	CustoTotalReparacao calcularPrecoRep(String repID);

	/**
	 * 
	 * @param orcId
	 */
	void generateOrc(String orcId);

	/**
	 * 
	 * @param repID
	 * @param mins
	 * @param custo
	 */
	void registaPasso(String repID, Integer mins, Double custo);

	/**
	 * 
	 * @param nif
	 */
	Cliente getCliente(String nif);

	/**
	 * 
	 * @param equipID
	 */
	Equipamento getEquipamento(String equipID);

	/**
	 * 
	 * @param nif
	 */
	Boolean existeCliente(String nif);

	/**
	 * 
	 * @param codR
	 * @param marca
	 */
	Boolean existeEquipamento(String codR, String marca);

	List<Equipamento> getEqProntoLevantar();

	/**
	 * 
	 * @param nif
	 * @param numero
	 * @param email
	 */
	void registaCliente(String nif, String numero, String email);

	/**
	 * 
	 * @param codR
	 * @param marca
	 * @param nif
	 */
	void registaEquipamento(String codR, String marca, String nif);

	/**
	 * 
	 * @param equiID
	 * @param state
	 */
	void alteraEstadoEq(String equiID, EstadoEquipamento state);

	/**
	 * 
	 * @param orcID
	 * @param nomePasso
	 * @param mat
	 * @param tempo
	 * @param qMat
	 * @param custoMat
	 */
	void criarPasso(String orcID, String nomePasso, String mat, Integer tempo, Integer qMat, Double custoMat);

	/**
	 * 
	 * @param nif
	 * @param equipId
	 * @param descr
	 */
	void registarOrcamento(String nif, String equipId, String descr);

	/**
	 * 
	 * @param repID
	 * @param tecID
	 * @param msg
	 */
	void registaContacto(String repID, String tecID, String msg);

	void registaColaborador(Colaborador c);

	/**
	 * 
	 * @param de
	 * @param ate
	 */
	Map<FuncionarioBalcao, List<Equipamento>> getEquipRecebidos(LocalDateTime de, LocalDateTime ate);

	/**
	 * 
	 * @param de
	 * @param ate
	 */
	Map<FuncionarioBalcao, List<Equipamento>> getEquipEntregue(LocalDateTime de, LocalDateTime ate);

	void saveInstance();

	/**
	 * 
	 * @param orcId
	 * @param passos
	 */
	void registaPT(String orcId, List<PassoReparacao> passos);

	/**
	 * 
	 * @param orcId
	 * @param msg
	 * @param tecID
	 */
	void comunicarErro(String orcId, String msg, String tecID);

	/**
	 * 
	 * @param data
	 */
	Map<Tecnico, List<ReparacoesPorMes>> getReparacoesMes(LocalDateTime data);

	/**
	 * 
	 * @param nome
	 * @param tempo
	 * @param custo
	 */
	void registarRepExpresso(String nome, Integer tempo, Double custo);

}