package ReparacoesLN.SSClientes;

import java.util.List;
import java.util.function.Predicate;

public interface IGestClientes {

	/**
	 * 
	 * @param nif
	 */
	Cliente getCliente(String nif);

	/**
	 * 
	 * @param equipID
	 */
	Equipamento getEquipamento(String equipID);

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
	 */
	void registaEquipamento(String codR, String marca, String nif);

	/**
	 * 
	 * @param equiID
	 * @param state
	 */
	void alteraEstadoEq(String equiID, EstadoEquipamento state);

	/**
	 * 
	 * @param p
	 */
	List<Equipamento> filterEquipamentos(Predicate<Equipamento> p);

}