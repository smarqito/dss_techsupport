package ReparacoesLN.SSColaboradores;

import ReparacoesLN.SSClientes.*;

public interface IGestColaboradores {

	void registaColaborador();

	/**
	 * 
	 * @param tipo
	 * @param de
	 * @param ate
	 */
	Map<Funcionario, List<Equipamento>> getEquipFuncBalcao(Class tipo, DateTime de, DateTime ate);

	/**
	 * 
	 * @param de
	 * @param ate
	 */
	Map<Funcionario, List<Equipamento>> getEquipRecebidos(DateTime de, DateTime ate);

	/**
	 * 
	 * @param de
	 * @param ate
	 */
	Map<Funcionario, List<Equipamento>> getEquipEntregue(DateTime de, DateTime ate);

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
	DateTime addEventoAgenda(String tecId, Integer tempo, String detalhes);

	/**
	 * 
	 * @param tempo
	 * @param detalhes
	 */
	DateTime addEventoAgenda(Integer tempo, String detalhes);

	/**
	 * 
	 * @param tecId
	 * @param data
	 */
	void removeEventoAgenda(String tecId, DateTime data);

	/**
	 * 
	 * @param duracao
	 */
	TecData prazoReparacaoMaisProx(Integer duracao);

}