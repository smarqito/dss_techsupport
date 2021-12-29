package ReparacoesLN.SSReparacoes;

import ReparacoesLN.SSColaboradores.*;
import ReparacoesLN.SSClientes.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import Middleware.EstadoOrcNaoEValidoException;
import Middleware.ReparacaoExpressoJaExisteException;
import Middleware.ReparacaoNaoExisteException;

public interface IGestReparacoes {

	List<Orcamento> getOrcamentosAtivos();

	/**
	 * 
	 * @param ref
	 */
	Orcamento getOrcamento(String ref);

	/**
	 * 
	 * @param id
	 */
	Reparacao getReparacao(String id) throws ReparacaoNaoExisteException;

	/**
	 * 
	 * @param p
	 */
	List<Orcamento> filterOrcamentos(Predicate<Orcamento> p);

	/**
	 * 
	 * @param orcID
	 * @param estado
	 * @throws EstadoOrcNaoEValidoException
	 */
	void alterarEstadoOrc(String orcID, OrcamentoEstado estado) throws EstadoOrcNaoEValidoException;

	/**
	 * 
	 * @param repID
	 * @param estado
	 */
	void alterarEstadoRep(String repID, ReparacaoEstado estado);

	/**
	 * 
	 * @param repID
	 * @param estado
	 * @param comentario
	 */
	void alterarEstadoRep(String repID, ReparacaoEstado estado, String comentario);

	/**
	 * 
	 * @param nomeRepXpresso
	 */
	Integer getTempoEstimado(String nomeRepXpresso);

	/**
	 * 
	 * @param nomePasso
	 */
	Boolean existePasso(String nomePasso);

	/**
	 * 
	 * @param nomeCategoria
	 */
	Boolean existeCategoria(int nomeCategoria);

	/**
	 * 
	 * @param nome
	 */
	Boolean existeRepXpresso(String nome);

	/**
	 * 
	 * @param nomeCategoria
	 */
	void registaCategoria(String nomeCategoria);

	/**
	 * 
	 * @param repId
	 * @param msg
	 * @param tec
	 * @throws ReparacaoNaoExisteException
	 */
	void registaContacto(String repId, String msg, Tecnico tec) throws ReparacaoNaoExisteException;

	/**
	 * 
	 * @param repID
	 * @param mins
	 * @param custo
	 */
	void registaPasso(String repID, Integer mins, Double custo);

	/**
	 * Método para registar uma nova reparação expresso nas reparações disponíveis
	 * Caso a reparação seja a mesma não é adicionada -> throw Exception
	 * 
	 * @param nome Nome da nova Reparação Expresso
	 * @param desc Descrição da nova reparação
	 * @param preco Preço fixo da nova reparação
	 * @param tempo Tempo estimado da nova reparação
	 * @throws ReparacaoExpressoJaExisteException
	 */
	void registaRepXpresso(String nome, Double preco, Integer tempo) throws ReparacaoExpressoJaExisteException;

	/**
	 * 
	 * @param equip
	 * @param descr
	 */
	void registarOrcamento(Equipamento equip, String descr);

	/**
	 * 
	 * @param orcId
	 * @param passos
	 */
	void registaPT(String orcId, List<PassoReparacao> passos);

	/**
	 * 
	 * @param equip
	 * @param nomeRepXpresso
	 * @param tec
	 */
	void addRepExpresso(Equipamento equip, String nomeRepXpresso, Tecnico tec);

	/**
	 * 
	 * @param orc
	 */
	void addReparacao(Orcamento orc);

	/**
	 * 
	 * @param orc
	 */
	Reparacao criarReparacao(Orcamento orc);

	/**
	 * 
	 * @param repID
	 */
	CustoTotalReparacao calcularPrecoRep(String repID) throws ReparacaoNaoExisteException;

	Reparacao pedidoRepMaisUrg();

	/**
	 * 
	 * @param orcId
	 */
	void generateOrc(String orcId);

	/**
	 * 
	 * @param orcId
	 */
	void enviarOrcamento(String orcId);

	/**
	 * 
	 * @param orc
	 * @param tec
	 * @param msg
	 */
	void comunicarErro(Orcamento orc, Tecnico tec, String msg);

	/**
	 * 
	 * @param msg
	 * @param dest
	 */
	void enviarEmail(String msg, String dest);

	/**
	 * 
	 * @param orcID
	 * @param nomePasso
	 * @param mat
	 * @param tempo
	 */
	void criarPasso(String orcID, String nomePasso, Material mat, Integer tempo);

	void arquivarOrcamentos();

	/**
	 * 
	 * @param data
	 */
	Map<Tecnico, ReparacoesPorMes> getReparacoesMes(LocalDateTime data);

}