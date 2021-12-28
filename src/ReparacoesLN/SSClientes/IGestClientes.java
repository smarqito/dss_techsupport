package ReparacoesLN.SSClientes;

import java.util.List;
import java.util.function.Predicate;

import Middleware.ClienteNaoExisteException;
import Middleware.EquipamentoNaoExisteException;

public interface IGestClientes {

	/**
	 * 
	 * @param nif
	 * @throws ClienteNaoExisteException
	 */
	Cliente getCliente(String nif) throws ClienteNaoExisteException;

	/**
	 * Obtem um equipamento caso este existe 
	 * @param equipID Id do equipamento a procurar
	 * @throws EquipamentoNaoExisteException
	 */
	Equipamento getEquipamento(String equipID) throws EquipamentoNaoExisteException;

	/**
	 * 
	 * @param nif
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
	 */
	void registaCliente(String nif, String numero, String email);

	/**
	 * 
	 * @param codR
	 * @param marca
	 * @param nif
	 * @throws ClienteNaoExisteException
	 */
	void registaEquipamento(String codR, String marca, String nif) throws ClienteNaoExisteException;

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

}