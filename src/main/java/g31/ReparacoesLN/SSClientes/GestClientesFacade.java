package g31.ReparacoesLN.SSClientes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import g31.Middleware.ClienteJaExisteException;
import g31.Middleware.ClienteNaoExisteException;
import g31.Middleware.EquipamentoJaAssociadoException;
import g31.Middleware.EquipamentoNaoExisteException;

public class GestClientesFacade implements IGestClientes, Serializable {

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
	public void registaCliente(String nif, String numero, String email) throws ClienteJaExisteException {
		if (!existeCliente(nif)) {
			Cliente newC = new Cliente(nif, new FormaContacto(email, numero));
			this.clientes.put(nif, newC);
		}
		throw new ClienteJaExisteException(nif);
	}

	@Override
	public void registaEquipamento(String codR, String marca, String nif)
			throws ClienteNaoExisteException, EquipamentoJaAssociadoException {
		Cliente c = getCliente(nif);
		Equipamento equip = new Equipamento(codR, marca, c);
		this.equipamentos.put(equip.getId(), equip);
		associaEquipamentoCliente(c, equip);
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

	@Override
	public void associaEquipamentoCliente(Cliente c, Equipamento e) throws EquipamentoJaAssociadoException {
		if (this.equipamentos.values().stream()
				.noneMatch(x -> x.getCodRegisto().equals(e.getCodRegisto()) && x.getMarca().equals(e.getMarca()))) {
			c.addEquipamento(e);
		} else {
			throw new EquipamentoJaAssociadoException(
					"O equipamento ja se encontra associado a algum cliente! Verifique antes de inserir.");
		}
	}

	@Override
	public List<Equipamento> darBaixaEquipamentos() {
		LocalDateTime porArq = LocalDateTime.now().minusDays(90).withHour(0).withMinute(0).withSecond(0);
		List<Equipamento> abandonados = this.equipamentos
				.values()
				.stream()
				.filter(
						x -> x.getEstado().equals(EstadoEquipamento.prontoLevantar)
								&& x.getUltimoEstado().isBefore(porArq))
				.collect(Collectors.toList());
		abandonados.stream().forEach(x -> x.setEstado(EstadoEquipamento.abandonado));
		return abandonados;
	}

}