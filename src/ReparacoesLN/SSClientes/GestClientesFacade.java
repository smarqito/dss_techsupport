package ReparacoesLN.SSClientes;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Middleware.ClienteNaoExisteException;
import Middleware.EquipamentoNaoExisteException;

public class GestClientesFacade implements IGestClientes {

	private Map<String, Cliente> clientes;
	private Map<String, Equipamento> equipamentos;

	@Override
	public Cliente getCliente(String nif) throws ClienteNaoExisteException {
		if (clientes.containsKey(nif)) {
			return clientes.get(nif);
		}
		throw new ClienteNaoExisteException(nif);
	}

	@Override
	public Equipamento getEquipamento(String equipID) throws EquipamentoNaoExisteException {
		if (equipamentos.containsKey(equipID)) {
			return equipamentos.get(equipID);
		}
		throw new EquipamentoNaoExisteException(equipID);
	}

	@Override
	public Equipamento getEquipamento(String codR, String marca) throws EquipamentoNaoExisteException {
		try {
			return equipamentos.values().stream()
					.filter(x -> x.getCodRegisto().equals(codR) && x.getMarca().equals(marca)).findFirst().get();
		} catch (NoSuchElementException e) {
			throw new EquipamentoNaoExisteException(codR + " marca " + marca);
		}
	}

	@Override
	public Boolean existeCliente(String nif) {
		try {
			this.getCliente(nif);
		} catch (ClienteNaoExisteException e) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean existeEquipamento(String codR, String marca) {
		return this.equipamentos.values().stream()
				.anyMatch(x -> x.getCodRegisto().equals(codR) && x.getMarca().equals(marca));
	}

	@Override
	public List<Equipamento> getEqProntoLevantar() {
		Predicate<Equipamento> p = x -> (x.getEstado().equals(EstadoEquipamento.prontoLevantar));
		return this.filterEquipamentos(p);
	}

	@Override
	public void registaCliente(String nif, String numero, String email) {
		Cliente newC = new Cliente(nif, new FormaContacto(email, numero));
	}

	@Override
	public void registaEquipamento(String codR, String marca, String nif) throws ClienteNaoExisteException {
		Equipamento e = new Equipamento(codR, marca, getCliente(nif));
	}

	@Override
	public void alteraEstadoEq(String equiID, EstadoEquipamento state) throws EquipamentoNaoExisteException {
		Equipamento eq = getEquipamento(equiID);
		eq.setState(state);
	}

	@Override
	public List<Equipamento> filterEquipamentos(Predicate<Equipamento> p) {
		return this.equipamentos.values().stream().filter(p).collect(Collectors.toList());
	}

}