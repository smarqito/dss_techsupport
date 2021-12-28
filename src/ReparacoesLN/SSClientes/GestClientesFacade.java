package ReparacoesLN.SSClientes;

import Middleware.EquipaNaoExisteException;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GestClientesFacade implements IGestClientes {

	private Cliente clientes;
	private Map<String, Equipamento> equipamentos;

	/**
	 * 
	 * @param nif
	 */
	public Cliente getCliente(String nif) {
		// TODO - implement GestClientesFacade.getCliente
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param equipID
	 */
	public Equipamento getEquipamento(String equipID) throws EquipaNaoExisteException {
		if (this.equipamentos.containsKey(equipID))
				return this.equipamentos.get(equipID);
		else
			throw new EquipaNaoExisteException("Equipamento com id: "  + equipID + " não existe no sistema!");

	}

	/**
	 * 
	 * @param nif
	 */
	public Boolean existeCliente(String nif) {
		// TODO - implement GestClientesFacade.existeCliente
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param codR
	 * @param marca
	 */
	public Boolean existeEquipamento(String codR, String marca) {
		// TODO - implement GestClientesFacade.existeEquipamento
		throw new UnsupportedOperationException();
	}

	public List<Equipamento> getEqProntoLevantar() {
		Predicate<Equipamento> p = x -> (x.getEstado().equals(EstadoEquipamento.prontoLevantar));
		return this.filterEquipamentos(p);
	}

	/**
	 * 
	 * @param nif
	 * @param numero
	 * @param email
	 */
	public void registaCliente(String nif, String numero, String email) {
		Cliente newC = new Cliente(nif, new FormaContacto(email, numero));
	}

	/**
	 * 
	 * @param codR
	 * @param marca
	 * @param nif
	 */
	public void registaEquipamento(String codR, String marca, String nif) {
		Equipamento e = new Equipamento(codR, marca, getCliente(nif));
	}

	/**
	 * 
	 * @param equiID
	 * @param state
	 */
	public void alteraEstadoEq(String equiID, EstadoEquipamento state) throws EquipaNaoExisteException {
		Equipamento eq = getEquipamento(equiID);
		eq.setState(state);
	}

	/**
	 * 
	 * @param p
	 */
	public List<Equipamento> filterEquipamentos(Predicate<Equipamento> p) {
		return this.equipamentos.values().stream().filter(p).collect(Collectors.toList());
	}

}