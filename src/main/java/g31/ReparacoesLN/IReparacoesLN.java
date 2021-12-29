package g31.ReparacoesLN;

import g31.Middleware.*;
import g31.ReparacoesLN.SSReparacoes.Orcamento.Orcamento;
import g31.ReparacoesLN.SSReparacoes.Orcamento.OrcamentoEstado;
import g31.ReparacoesLN.SSReparacoes.PlanoTrabalho.PassoReparacao;
import g31.ReparacoesLN.SSReparacoes.Reparacao.CustoTotalReparacao;
import g31.ReparacoesLN.SSReparacoes.Reparacao.Reparacao;
import g31.ReparacoesLN.SSReparacoes.Reparacao.ReparacaoEstado;
import g31.ReparacoesLN.SSReparacoes.Reparacao.ReparacoesPorMes;
import g31.ReparacoesLN.SSClientes.*;
import g31.ReparacoesLN.SSColaboradores.Agenda.AgendaPorDia;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.Colaborador;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.FuncionarioBalcao;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.Tecnico;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IReparacoesLN {

	/**
	 * Método que adiciona ás reparações por realizar uma reparação expresso nova
	 * Só acontece se houver disponibilidade e a reparação expresso pedida
	 * corresponde
	 * a um tipo existente na pool de reparações expresso válidas
	 * 
	 * Este método utiliza outro do GestReparacoesFacade para realizar a adição.
	 * 
	 * @param equipId    Identificador do equipamento a adicionar
	 * @param nomeRepExp Nome da reparação a realizar
	 * @throws EquipamentoNaoExisteException
	 * @throws ReparacaoNaoExisteException
	 * @throws NaoExisteDisponibilidadeException
	 * @throws ColaboradorNaoExisteException
	 * @throws ColaboradorNaoTecnicoException
	 * @throws TecnicoNaoTemAgendaException
	 */
	String addRepExpresso(String equipId, String nomeRepExp, String funcId)
			throws EquipamentoNaoExisteException, ReparacaoNaoExisteException, NaoExisteDisponibilidadeException,
			ColaboradorNaoTecnicoException, ColaboradorNaoExisteException, TecnicoNaoTemAgendaException;

	/**
	 * 
	 * @param orcId
	 * @param colabId
	 * @throws ColaboradorNaoExisteException
	 * @throws OrcamentoNaoExisteException
	 */
	void enviarOrcamento(String orcId, String colabId)
			throws ColaboradorNaoExisteException, OrcamentoNaoExisteException;

	Set<Orcamento> getOrcamentosAtivos();

	/**
	 * Altera o estado do orcamento.
	 * Caso o novo estado corresponda a aceite, cria uma nova reparacao para o prazo
	 * mais proximo disponivel
	 * associa, ainda, o prazo de reparacao no orcamento
	 * 
	 * @param orcID
	 * @param estado
	 * @throws EstadoOrcNaoEValidoException
	 * @throws OrcamentoNaoExisteException
	 * @throws ColaboradorNaoExisteException
	 * @throws ColaboradorNaoTecnicoException
	 * @throws TecnicoNaoTemAgendaException
	 * @throws NaoExisteDisponibilidadeException
	 */
	void alterarEstadoOrc(String orcID, OrcamentoEstado estado)
			throws EstadoOrcNaoEValidoException, OrcamentoNaoExisteException, ColaboradorNaoTecnicoException,
			ColaboradorNaoExisteException, NaoExisteDisponibilidadeException, TecnicoNaoTemAgendaException;

	/**
	 * Altera o estado de reparacao para o novo estado
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
	 * Calcula o preco de uma reparacao utilizando o custo efetivo e tempo efetivo
	 * 
	 * @param repID ID da reparacao
	 * @throws ReparacaoNaoExisteException Caso a reparacao nao exista
	 */
	CustoTotalReparacao calcularPrecoRep(String repID) throws ReparacaoNaoExisteException;

	/**
	 * Metodo nao implementado.
	 * Seria utilizado para gerar um ficheiro PDF com o orcamento
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
	 * @param mins  Tempo Efetivo gasto
	 * @param custo Custo efetivo gasto
	 * @throws ReparacaoNaoExisteException
	 */
	void registaPasso(String repID, Integer mins, Double custo) throws ReparacaoNaoExisteException;

	/**
	 * Retorna um cliente a partir do seu nif
	 * 
	 * @param nif Nif do cliente a procurar
	 * @throws ClienteNaoExisteException Caso o cliente nao exista
	 */
	Cliente getCliente(String nif) throws ClienteNaoExisteException;

	/**
	 * Retorna um equipamento a partir do seu id
	 * 
	 * @param equipID Id do equipamento a procurar
	 * @throws EquipamentoNaoExisteException Caso o equipamento nao exista
	 */
	Equipamento getEquipamento(String equipID) throws EquipamentoNaoExisteException;

	/**
	 * Retorna equipamento a partir do seu codigo de registo (n' serie) e marca
	 * 
	 * @param codR  Codigo de registo
	 * @param marca Marca
	 * @throws EquipamentoNaoExisteException Caso o Equipamento nao exista
	 */
	Equipamento getEquipamento(String codR, String marca) throws EquipamentoNaoExisteException;

	/**
	 * Verifica se um cliente existe
	 * 
	 * @param nif Nif do cliente a ser verificado
	 */
	Boolean existeCliente(String nif);

	/**
	 * Verifica se um equipamento existe a partir do seu codigo de registo (n'
	 * serie) e marca
	 * 
	 * @param codR  Codigo de registo
	 * @param marca Marca
	 */
	Boolean existeEquipamento(String codR, String marca);

	List<Equipamento> getEqProntoLevantar();

	/**
	 * Regista um novo cliente
	 * 
	 * @param nif
	 * @param numero
	 * @param email
	 * @throws ClienteJaExisteException
	 */
	void registaCliente(String nif, String numero, String email) throws ClienteJaExisteException;

	/**
	 * Efetua o registo de um equipamento e associa-o ao cliente
	 * 
	 * @param codR  Codigo de registo
	 * @param marca Marca do equipamento
	 * @param nif   Nif do cliente proprietario
	 * @throws ClienteNaoExisteException       Caso o cliente com nif indicado nao
	 *                                         exista
	 * @throws EquipamentoJaAssociadoException Caso o equipamento ja esteja
	 *                                         associado ao cliente (ou outro)
	 * @return
	 */
	String registaEquipamento(String codR, String marca, String nif)
			throws ClienteNaoExisteException, EquipamentoJaAssociadoException;

	/**
	 * Altera o estado de um equipamento
	 * 
	 * @param equiID Id do equipamento
	 * @param state  Novo estado
	 * @throws EquipamentoNaoExisteException
	 */
	void alteraEstadoEq(String equiID, EstadoEquipamento state) throws EquipamentoNaoExisteException;

	/**
	 * Altera o estado de um equipamento
	 *
	 * @param equiID Id do equipamento
	 * @param state  Novo estado
	 * @param funcId Id do Funcionário
	 * @throws EquipamentoNaoExisteException
	 */
	void alteraEstadoEq(String equiID, EstadoEquipamento state, String funcId) throws EquipamentoNaoExisteException, ColaboradorNaoExisteException;

	/**
	 * Método que cria um novo passo e insere esse passo no plano de trabalhos de um
	 * orçamento
	 * 
	 * Utiliza o método do GestReparacoesFacade com o mesmo nome
	 * 
	 * @param orcID     Orçamento a realizar
	 * @param nomePasso Nome do Passo a criar
	 * @param mat       Todos os materiais usados, separados por ","
	 * @param tempo     Tempo estimado
	 * @param qMat      Quantidade de material usado
	 * @param custoMat  Custo total do material usado
	 * @throws PassoJaExisteException
	 */
	void criarPasso(String orcID, String nomePasso, String mat, Integer tempo, Integer qMat, Double custoMat)
			throws PassoJaExisteException;

	/**
	 * Regista um novo orcamento, associado a um cliente (nif) e um equipamento
	 * (equipId)
	 * 
	 * @param nif
	 * @param equipId
	 * @param descr
	 * @throws EquipamentoNaoExisteException
	 * @return
	 */
	String registarOrcamento(String nif, String equipId, String descr, String funcId) throws EquipamentoNaoExisteException, EquipamentoNaoAssociadoAoCliente, ColaboradorNaoExisteException;

	/**
	 * Adiciona um contacto ao historico de reparacao
	 * 
	 * @param repID
	 * @param tecID
	 * @param msg
	 * @throws ReparacaoNaoExisteException
	 * @throws ColaboradorNaoExisteException
	 * @throws ColaboradorNaoTecnicoException
	 */
	void registaContacto(String repID, String tecID, String msg)
			throws ReparacaoNaoExisteException, ColaboradorNaoTecnicoException, ColaboradorNaoExisteException;

	/**
	 * 
	 * @param nome
	 * @param tipo
	 * @throws TecnicoJaTemAgendaException
	 * @throws TipoColaboradorErradoException
	 */
	void registaColaborador(String nome, Class<? extends Colaborador> tipo)
			throws TecnicoJaTemAgendaException, TipoColaboradorErradoException;

	/**
	 * Calcula os equipamentos recebidos por funcionario de balcao
	 * 
	 * @param de
	 * @param ate
	 */
	Map<FuncionarioBalcao, List<Equipamento>> getEquipRecebidos(LocalDateTime de, LocalDateTime ate);

	/**
	 * Calcula os equipamentos recebidos por funcionario de balcao
	 * 
	 * @param de
	 * @param ate
	 */
	Map<FuncionarioBalcao, List<Equipamento>> getEquipEntregue(LocalDateTime de, LocalDateTime ate);

	/**
	 * Armazena a o estado do programa num ficheiro
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	void saveInstance() throws FileNotFoundException, IOException;

	/**
	 * Regista os passos num orcamento a partir do orcId
	 * 
	 * @param orcId
	 * @param passos
	 * @throws OrcamentoNaoExisteException
	 */
	void registaPT(String orcId, List<PassoReparacao> passos) throws OrcamentoNaoExisteException;

	/**
	 * Adiciona um erro ao historico da reparacao (same as regista contacto...)
	 * 
	 * @param orcId
	 * @param msg
	 * @param tecID
	 * @throws OrcamentoNaoExisteException
	 * @throws ColaboradorNaoExisteException
	 * @throws ColaboradorNaoTecnicoException
	 */
	void comunicarErro(String orcId, String msg, String tecID)
			throws OrcamentoNaoExisteException, ColaboradorNaoTecnicoException, ColaboradorNaoExisteException;

	/**
	 * Calcula as reparacoes para um dado mes, agrupadas por tecnico
	 * 
	 * @param data
	 */
	Map<Tecnico, ReparacoesPorMes> getReparacoesMes(LocalDateTime data);

	/**
	 * Método para registar uma nova reparação expresso
	 * Adiciona a reparação à lista de reparações expresso disponiveis
	 * 
	 * Utiliza o método com o mesmo nome
	 * 
	 * @param nome  Nome da nova reparação
	 * @param tempo Tempo estimado para a reparação
	 * @param custo Custo fixo da reparação
	 * @throws ReparacaoExpressoJaExisteException
	 */
	void registarRepExpresso(String nome, Integer tempo, Double custo) throws ReparacaoExpressoJaExisteException;

	/**
	 * Calcula o orcamento mais antigo para ser analisado
	 * 
	 * @return
	 * @throws NaoExisteOrcamentosAtivosException
	 */
	Orcamento getOrcamentoMaisAntigo() throws NaoExisteOrcamentosAtivosException;

	/**
	 * Método que retorna um orçamento dado a sua referência (ID)
	 * 
	 * @param ref Identificador a procurar
	 * 
	 * @return Orcamento com este identificador
	 * @throws OrcamentoNaoExisteException
	 */
	Orcamento getOrcamento(String ref) throws OrcamentoNaoExisteException;

	/**
	 * Arquiva os orcamentos que passaram de prazo (30 dias)
	 * 
	 * @return Orcamentos que foi mudado estado
	 */
	List<Orcamento> arquivaOrcamentos();

	/**
	 * Da baixa a equipamentos prontos a levantar ha mais de 90 dias
	 * 
	 * @return Equipamentos ao qual foi dado baixa
	 */
	List<Equipamento> darBaixaEquipamentos();

	/**
	 * Verifica se um colaborador existe
	 * @param id Identificacao do colaborador
	 * @return
	 */
	boolean existeColaborador(String id);

	/**
	 * Retorna um colaborador, caso este exista
	 * @param id Identificacao do colaborador
	 * @return Colaborador
	 * @throws ColaboradorNaoExisteException Caso nao exista
	 */
	Colaborador getColaborador(String id) throws ColaboradorNaoExisteException;

	/**
	 * Retorna uma reparacao a partir do ID
	 * @param id ID da reparacao a procurar
	 * @return Reparacao
	 * @throws ReparacaoNaoExisteException
	 */
	Reparacao getReparacao(String id) throws ReparacaoNaoExisteException;

	/**
	 * Calcula a agenda do dia
	 * 
	 * @param data Dia a ser procurado
	 * @param tecId Tecnico que se pretende a agenda
	 * @return AgendaPorDia
	 * @throws TecnicoNaoTemAgendaException
	 */
	AgendaPorDia getAgendaDia(LocalDate data, String tecId) throws TecnicoNaoTemAgendaException;
}