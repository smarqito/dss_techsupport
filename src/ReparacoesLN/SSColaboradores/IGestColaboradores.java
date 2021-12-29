package ReparacoesLN.SSColaboradores;

import ReparacoesLN.SSClientes.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import Middleware.EntradaNaoExisteException;
import Middleware.NaoExisteDisponibilidadeException;
import Middleware.TecnicoJaTemAgendaException;

public interface IGestColaboradores {

	void registaColaborador(String nome, String tipo) throws TecnicoJaTemAgendaException;

	/**
	 * 
	 * @param tipo
	 * @param de
	 * @param ate
	 */
	Map<FuncionarioBalcao, List<Equipamento>> getEquipFuncBalcao(Colaborador tipo, LocalDateTime de, LocalDateTime ate);

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

	/**
	 * 
	 * @param tecID
	 */
	Tecnico getTecnico(String tecID);

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