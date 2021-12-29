package g31.ReparacoesLN.SSClientes;

import java.util.List;
import java.util.function.Predicate;

import g31.Middleware.ClienteJaExisteException;
import g31.Middleware.ClienteNaoExisteException;
import g31.Middleware.EquipamentoJaAssociadoException;
import g31.Middleware.EquipamentoNaoExisteException;

public interface IGestClientes {

	/**
	 * 
	 * @param nif
	 * @throws ClienteNaoExisteException
	 */
	Cliente getCliente(String nif) throws ClienteNaoExisteException;

	/**
	 * Obtem um equipamento caso este existe
	 * 
	 * @param equipID Id do equipamento a procurar
	 * @throws EquipamentoNaoExisteException
	 */
	Equipamento getEquipamento(String equipID) throws EquipamentoNaoExisteException;

	/**
	 * Obtem um equipamento caso este existe
	 * 
	 * @param equipID Id do equipamento a procurar
	 * @throws EquipamentoNaoExisteException
	 */
	Equipamento getEquipamento(String codR, String marca) throws EquipamentoNaoExisteException;

	/**
	 * Verifica se um cliente existe
	 * 
	 * @param nif Nif do cliente
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
	 * @throws ClienteJaExisteException
	 */
	void registaCliente(String nif, String numero, String email) throws ClienteJaExisteException;

	/**
	 * 
	 * @param codR
	 * @param marca
	 * @param nif
	 * @throws ClienteNaoExisteException
	 * @throws EquipamentoJaAssociadoException
	 */
	void registaEquipamento(String codR, String marca, String nif)
			throws ClienteNaoExisteException, EquipamentoJaAssociadoException;

	/**
	 * 
	 * @param equiID
	 * @param state
	 * @throws EquipamentoNaoExisteException
	 */
	void alteraEstadoEq(String equiID, EstadoEquipamento state) throws EquipamentoNaoExisteException;

	/**
	 * 
	 * @param p
	 */
	List<Equipamento> filterEquipamentos(Predicate<Equipamento> p);

	/**
	 * Associa um equipamento a um cliente
	 * 
	 * @param c Cliente proprietario
	 * @param e Equipamento
	 * @throws EquipamentoJaAssociadoException Caso a associacao ja exista
	 */
	void associaEquipamentoCliente(Cliente c, Equipamento e) throws EquipamentoJaAssociadoException;

	/**
	 * Alterar estado equipamento para abandonado, caso pronto a levantar ha mais de
	 * 90 dias
	 * 
	 * @return Equipamentos abandonados
	 */
	List<Equipamento> darBaixaEquipamentos();
}