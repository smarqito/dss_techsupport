package ReparacoesLN.SSColaboradores;

import ReparacoesLN.SSClientes.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import Middleware.ColaboradorNaoExisteException;
import Middleware.ColaboradorNaoTecnicoException;
import Middleware.EntradaNaoExisteException;
import Middleware.NaoExisteDisponibilidadeException;
import Middleware.TecnicoJaTemAgendaException;
import Middleware.TipoColaboradorErradoException;

public interface IGestColaboradores {

	String registaColaborador(String nome, Class<? extends Colaborador> tipo) throws TecnicoJaTemAgendaException, TipoColaboradorErradoException;

	/**
	 * Método que constroi um map com os equipamentos que passaram por um funcionário de balcão
	 * Constroi um map para apenas uma interação: Receção ou Entrega
	 * 
	 * @param tipo Tipo de interação
	 * @param de Data limite miníma
	 * @param ate Data limite máxima
	 */
	Map<FuncionarioBalcao, List<Equipamento>> getEquipFuncBalcao(Class<? extends Balcao> tipo, LocalDateTime de, LocalDateTime ate);

	/**
	 * Método que retorna um map de todos os equipamentos recebedidos
	 * Todos os equipamentos que passaram pela interação Receção com o funcionário de balcão
	 * 
	 * @param de Data limite miníma
	 * @param ate Data limite máxima
	 */
	Map<FuncionarioBalcao, List<Equipamento>> getEquipRecebidos(LocalDateTime de, LocalDateTime ate);

	/**
	 * Método que retorna um map de todos os equipamentos entregues
	 * Todos os equipamentos que passaram pela interação Entrega com o funcionário de balcão 
	 * 
	 * @param de Data limite miníma
	 * @param ate Data limite máxima
	 */
	Map<FuncionarioBalcao, List<Equipamento>> getEquipEntregue(LocalDateTime de, LocalDateTime ate);

	/**
	 * 
	 * @param tecID
	 * @throws ColaboradorNaoTecnicoException
	 * @throws ColaboradorNaoExisteException
	 */
	Tecnico getTecnico(String tecID) throws ColaboradorNaoTecnicoException, ColaboradorNaoExisteException;
	
	/**
	 * 
	 * @param colabId
	 * @return
	 * @throws ColaboradorNaoExisteException
	 */
	public Colaborador getColaborador(String colabId) throws ColaboradorNaoExisteException;

	/**
	 * 
	 * @param cod
	 */
	Boolean validaIdentificacao(String cod);

	/**
	 * 
	 * @param duracao
	 * @throws NaoExisteDisponibilidadeException
	 */
	String existeDisponibilidade(Integer duracao) throws NaoExisteDisponibilidadeException;

	/**
	 * 
	 * @param tecId
	 * @param tempo
	 * @param detalhes
	 * @throws NaoExisteDisponibilidadeException
	 */
	LocalDateTime addEventoAgenda(String tecId, Integer tempo, String detalhes) throws NaoExisteDisponibilidadeException;

	/**
	 * 
	 * @param tempo
	 * @param detalhes
	 * @throws NaoExisteDisponibilidadeException
	 */
	LocalDateTime addEventoAgenda(Integer tempo, String detalhes) throws NaoExisteDisponibilidadeException;

	/**
	 * 
	 * @param tecId
	 * @param data
	 * @throws EntradaNaoExisteException
	 */
	void removeEventoAgenda(String tecId, LocalDateTime data) throws EntradaNaoExisteException;

	/**
	 * 
	 * @param duracao
	 */
	TecData prazoReparacaoMaisProx(Integer duracao);

}