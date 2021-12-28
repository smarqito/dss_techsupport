package ReparacoesLN.SSClientes;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Middleware.ClienteNaoExisteException;

public class GestClientesFacade implements IGestClientes {

	private Map<String, Cliente> clientes;
	private Map<String, Equipamento> equipamentos;

	/**
	 * 
	 * @param nif
	 * @throws ClienteNaoExisteException
	 */
	public Cliente getCliente(String nif) throws ClienteNaoExisteException {
		if(clientes.containsKey(nif)) {
			return clientes.get(nif);
		}
		throw new ClienteNaoExisteException("nif");
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
	 * @throws ClienteNaoExisteException
	 */
	public void registaEquipamento(String codR, String marca, String nif) throws ClienteNaoExisteException {
		Equipamento e = new Equipamento(codR, marca, getCliente(nif));
	}

	/**
	 * 
	 * @param equiID
	 * @param state
	 */
	public void alteraEstadoEq(String equiID, EstadoEquipamento state) {
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