package g31.ReparacoesLN.SSReparacoes;

import g31.ReparacoesLN.SSColaboradores.*;
import g31.ReparacoesLN.SSClientes.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import g31.Middleware.EstadoOrcNaoEValidoException;
import g31.Middleware.NaoExisteOrcamentosAtivosException;
import g31.Middleware.OrcamentoNaoExisteException;
import g31.Middleware.PassoJaExisteException;
import g31.Middleware.ReparacaoExpressoJaExisteException;
import g31.Middleware.ReparacaoNaoExisteException;

public interface IGestReparacoes {

	/**
	 * 
	 * @return
	 */
	Set<Orcamento> getOrcamentosAtivos();

	/**
	 * 
	 * @param ref
	 * @throws OrcamentoNaoExisteException
	 */
	Orcamento getOrcamento(String ref) throws OrcamentoNaoExisteException;

	/**
	 * 
	 * @param id
	 */
	Reparacao getReparacao(String id) throws ReparacaoNaoExisteException;

	/**
	 * 
	 * @return
	 * @throws NaoExisteOrcamentosAtivosException
	 */
	Orcamento getOrcamentoMaisAntigo() throws NaoExisteOrcamentosAtivosException;
	/**
	 * 
	 * @param p
	 */
	List<Orcamento> filterOrcamentos(Predicate<Orcamento> p);


	/**
	 * 
	 * @param p
	 * @return
	 */
	List<Reparacao> filterReparacoes(Predicate<Reparacao> p);
	/**
	 * 
	 * @param orcID
	 * @param estado
	 * @throws EstadoOrcNaoEValidoException
	 * @throws OrcamentoNaoExisteException
	 */
	void alterarEstadoOrc(String orcID, OrcamentoEstado estado) throws EstadoOrcNaoEValidoException, OrcamentoNaoExisteException;

	/**
	 * 
	 * @param repID
	 * @param estado
	 * @throws ReparacaoNaoExisteException
	 */
	void alterarEstadoRep(String repID, ReparacaoEstado estado) throws ReparacaoNaoExisteException;

	/**
	 * 
	 * @param repID
	 * @param estado
	 * @param comentario
	 * @throws ReparacaoNaoExisteException
	 */
	void alterarEstadoRep(String repID, ReparacaoEstado estado, String comentario) throws ReparacaoNaoExisteException;

	/**
	 * 
	 * @param nomeRepXpresso
	 * @throws ReparacaoNaoExisteException
	 */
	Integer getTempoEstimado(String nomeRepXpresso) throws ReparacaoNaoExisteException;

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
	 * @param nome
	 * @return
	 * @throws ReparacaoNaoExisteException
	 */
	ReparacaoExpresso getReparacaoExpresso(String nome) throws ReparacaoNaoExisteException;

	/**
	 * Calcula a lista de todas as reparacoes disponiveis
	 * @return
	 */
	List<ReparacaoExpresso> getReparacaoExpresso();

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
	 * Método que regista a realização de um passo de reparação
	 * Atualiza os valores efetivos do passo atual a realizar-se
	 * Atualiza a lista de passos realizados
	 * Atualiza o próximo passo a ser realizado como atual
	 * 
	 * @param repID Identificador da reparação a realizar
	 * @param mins  Tempo efetivo da reparação
	 * @param custo Custo efetivo da reparação
	 * @throws ReparacaoNaoExisteException
	 */
	void registaPasso(String repID, Integer mins, Double custo) throws ReparacaoNaoExisteException;

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
	 * @throws OrcamentoNaoExisteException
	 */
	void registaPT(String orcId, List<PassoReparacao> passos) throws OrcamentoNaoExisteException;

	/**
	 * Método que adiciona ás reparações por realizar uma reparação expresso nova
	 * A reparação só é efetuada se o tipo de reparação existir no sistema
	 * 
	 * @param equip          Equipamento a reparar
	 * @param nomeRepXpresso Nome da reparação a efetuar
	 * @param tec            Técnico a realizar a reparação
	 * @throws ReparacaoNaoExisteException
	 */
	String addRepExpresso(Equipamento equip, String nomeRepXpresso, Tecnico tec) throws ReparacaoNaoExisteException;

	/**
	 * 
	 * @param orc
	 */
	String addReparacao(Orcamento orc, Tecnico tec);

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
	 * @throws OrcamentoNaoExisteException
	 */
	void enviarOrcamento(String orcId, Colaborador colaborador) throws OrcamentoNaoExisteException;

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
	 * Método que cria um novo passo e insere esse passo no plano de trabalhos de um
	 * orçamento
	 * 
	 * Esse passo não pode existir já no plano de trabalhos
	 * 
	 * @param orcID     Orçamento a realizar
	 * @param nomePasso Nome do passo a criar
	 * @param mat       Material usado no passo
	 * @param tempo     tempo estimado para o passo
	 * @throws PassoJaExisteException
	 */
	void criarPasso(String orcID, String nomePasso, Material mat, Integer tempo) throws PassoJaExisteException;
	
	/** 
     * Método que arquiva os orçamentos cujo prazo limite para serem aceites pelo cliente expiraram 
     * Filtra todso os Orcamentos que estejam ativos e ja passaram o prazo limite 
     *
     */
	List<Orcamento> arquivarOrcamentos();

	
	Map<Tecnico, ReparacoesPorMes> getReparacoesMes(LocalDateTime data);

}