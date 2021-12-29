package ReparacoesLN;

import ReparacoesLN.SSReparacoes.*;
import ReparacoesLN.SSClientes.*;
import ReparacoesLN.SSColaboradores.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import Middleware.ClienteJaExisteException;
import Middleware.ClienteNaoExisteException;
import Middleware.EquipamentoJaAssociadoException;
import Middleware.EquipamentoNaoExisteException;
import Middleware.EstadoOrcNaoEValidoException;
import Middleware.ReparacaoNaoExisteException;
import Middleware.TecnicoJaTemAgendaException;

public interface IReparacoesLN {

	/**
	 * Método que adiciona ás reparações por realizar uma reparação expresso nova
	 * Só acontece se houver disponibilidade e a reparação expresso pedida corresponde
	 * a um tipo existente na pool de reparações expresso válidas
	 * 
	 * Este método utiliza outro do GestReparacoesFacade para realizar a adição.
	 * 
	 * @param equipId Identificador do equipamento a adicionar 
	 * @param nomeRepExp Nome da reparação a realizar
	 * @throws EquipamentoNaoExisteException
	 */
	void addRepExpresso(String equipId, String nomeRepExp) throws EquipamentoNaoExisteException;

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
	 * Calcula o preco de uma reparacao utilizando o custo efetivo e tempo efetivo
	 * @param repID ID da reparacao
	 * @throws ReparacaoNaoExisteException Caso a reparacao nao exista
	 */
	CustoTotalReparacao calcularPrecoRep(String repID) throws ReparacaoNaoExisteException;

	/**
	 * 
	 * @param orcId
	 */
	void generateOrc(String orcId);

	/**
	 * Método que regista a realização de um passo de reparação
	 * 
	 * Consiste na chamada do método do GestReparacoesFacade com o mesmo nome
	 * 
	 * @param repID Reparação a realizar
	 * @param mins Tempo Efetivo gasto
	 * @param custo Custo efetivo gasto
	 */
	void registaPasso(String repID, Integer mins, Double custo);

	/**
	 * Retorna um cliente a partir do seu nif
	 * @param nif Nif do cliente a procurar
	 * @throws ClienteNaoExisteException Caso o cliente nao exista
	 */
	Cliente getCliente(String nif) throws ClienteNaoExisteException;

	/**
	 * Retorna um equipamento a partir do seu id
	 * @param equipID Id do equipamento a procurar
	 * @throws EquipamentoNaoExisteException Caso o equipamento nao exista
	 */
	Equipamento getEquipamento(String equipID) throws EquipamentoNaoExisteException;
	
	/**
	 * Retorna equipamento a partir do seu codigo de registo (n' serie) e marca
	 * @param codR Codigo de registo
	 * @param marca Marca
	 * @throws EquipamentoNaoExisteException Caso o Equipamento nao exista
	 */
	Equipamento getEquipamento(String codR, String marca) throws EquipamentoNaoExisteException;

	/**
	 * Verifica se um cliente existe
	 * @param nif Nif do cliente a ser verificado
	 */
	Boolean existeCliente(String nif);

	/**
	 * Verifica se um equipamento existe a partir do seu codigo de registo (n' serie) e marca
	 * @param codR Codigo de registo
	 * @param marca Marca
	 */
	Boolean existeEquipamento(String codR, String marca);

	List<Equipamento> getEqProntoLevantar();

	/**
	 * 
	 * @param nif
	 * @param numero
	 * @param email
	 * @throws ClienteJaExisteException
	 */
	void registaCliente(String nif, String numero, String email) throws ClienteJaExisteException;

	/**
	 * Efetua o registo de um equipamento e associa-o ao cliente
	 * @param codR Codigo de registo
	 * @param marca Marca do equipamento
	 * @param nif Nif do cliente proprietario
	 * @throws ClienteNaoExisteException	Caso o cliente com nif indicado nao exista
	 * @throws EquipamentoJaAssociadoException Caso o equipamento ja esteja associado ao cliente (ou outro)
	 */
	void registaEquipamento(String codR, String marca, String nif) throws ClienteNaoExisteException, EquipamentoJaAssociadoException;

	/**
	 * 
	 * @param equiID
	 * @param state
	 * @throws EquipamentoNaoExisteException
	 */
	void alteraEstadoEq(String equiID, EstadoEquipamento state) throws EquipamentoNaoExisteException;

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
	void criarPasso(String orcID, String nomePasso, String mat, Integer tempo, Integer qMat, Double custoMat);

	/**
	 * 
	 * @param nif
	 * @param equipId
	 * @param descr
	 * @throws EquipamentoNaoExisteException
	 */
	void registarOrcamento(String nif, String equipId, String descr) throws EquipamentoNaoExisteException;

	/**
	 * 
	 * @param repID
	 * @param tecID
	 * @param msg
	 * @throws ReparacaoNaoExisteException
	 */
	void registaContacto(String repID, String tecID, String msg) throws ReparacaoNaoExisteException;

	void registaColaborador(String nome, String tipo) throws TecnicoJaTemAgendaException;

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

	boolean existeColaborador(String id);

	Colaborador getColaborador(String id);
}