package ReparacoesLN.SSColaboradores;

import ReparacoesLN.SSClientes.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IGestColaboradores {

	void registaColaborador(String nome, String tipo);

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
	 */
	String existeDisponibilidade(Integer duracao);

	/**
	 * 
	 * @param tecId
	 * @param tempo
	 * @param detalhes
	 */
	LocalDateTime addEventoAgenda(String tecId, Integer tempo, String detalhes);

	/**
	 * 
	 * @param tempo
	 * @param detalhes
	 */
	LocalDateTime addEventoAgenda(Integer tempo, String detalhes);

	/**
	 * 
	 * @param tecId
	 * @param data
	 */
	void removeEventoAgenda(String tecId, LocalDateTime data);

	/**
	 * 
	 * @param duracao
	 */
	TecData prazoReparacaoMaisProx(Integer duracao);

}