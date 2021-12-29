package g31.ReparacoesLN.SSColaboradores;

import g31.ReparacoesLN.SSClientes.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import g31.Middleware.ColaboradorNaoExisteException;
import g31.Middleware.ColaboradorNaoTecnicoException;
import g31.Middleware.EntradaNaoExisteException;
import g31.Middleware.NaoExisteDisponibilidadeException;
import g31.Middleware.TecnicoJaTemAgendaException;
import g31.Middleware.TecnicoNaoTemAgendaException;
import g31.Middleware.TipoColaboradorErradoException;

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
	Boolean existeColaborador(String cod);

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
	 * @throws TecnicoNaoTemAgendaException
	 */
	LocalDateTime addEventoAgenda(String tecId, Integer tempo, String detalhes) throws NaoExisteDisponibilidadeException, TecnicoNaoTemAgendaException;

	/**
	 * 
	 * @param tempo
	 * @param detalhes
	 * @throws NaoExisteDisponibilidadeException
	 * @throws TecnicoNaoTemAgendaException
	 */
	LocalDateTime addEventoAgenda(Integer tempo, String detalhes) throws NaoExisteDisponibilidadeException, TecnicoNaoTemAgendaException;

	/**
	 * 
	 * @param tecId
	 * @param data
	 * @throws EntradaNaoExisteException
	 * @throws TecnicoNaoTemAgendaException
	 */
	void removeEventoAgenda(String tecId, LocalDateTime data) throws EntradaNaoExisteException, TecnicoNaoTemAgendaException;

	/**
	 * 
	 * @param duracao
	 */
	TecData prazoReparacaoMaisProx(Integer duracao);

	/**
	 * Calcula o plano da agenda para um dia, para um tecnico
	 * @param data dia a procurar
	 * @param tecId Tecnico a procurar
	 * @return Agenda Por Dia
	 * @throws TecnicoNaoTemAgendaException
	 */
	AgendaPorDia getAgendaDia(LocalDate data, String tecId) throws TecnicoNaoTemAgendaException;
}