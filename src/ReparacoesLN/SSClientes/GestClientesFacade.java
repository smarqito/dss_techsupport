package ReparacoesLN.SSClientes;

import java.util.List;
import java.util.function.Predicate;

public class GestClientesFacade implements IGestClientes {

	private Cliente clientes;
	private Equipamento equipamentos;

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
	public Equipamento getEquipamento(String equipID) {
		// TODO - implement GestClientesFacade.getEquipamento
		throw new UnsupportedOperationException();
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
		// TODO - implement GestClientesFacade.getEqProntoLevantar
		throw new UnsupportedOperationException();
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
	public void alteraEstadoEq(String equiID, EstadoEquipamento state) {
		// TODO - implement GestClientesFacade.alteraEstadoEq
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 */
	public List<Equipamento> filterEquipamentos(Predicate<Equipamento> p) {
		// TODO - implement GestClientesFacade.filterEquipamentos
		throw new UnsupportedOperationException();
	}

}