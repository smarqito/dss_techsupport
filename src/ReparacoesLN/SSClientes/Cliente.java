package ReparacoesLN.SSClientes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import Middleware.EquipamentoNaoAssociadoAoCliente;

public class Cliente implements Serializable {

	private Map<String, Equipamento> equipamentos;
	private FormaContacto contacto;
	private String nif;


	// public Cliente() {
	// 	this.equipamentos = new HashMap<>();
	// 	this.contacto = new FormaContacto();
	// 	this.nif = null;
	// }

	public Cliente(Map<String, Equipamento> e, FormaContacto fc, String nif) {
		setEquipamentos(e);
		setFormaContacto(fc);
		this.nif = nif;
	}

	public Cliente(String nif, FormaContacto fm) {
		this.equipamentos = new HashMap<>();
		setFormaContacto(fm);
		this.nif = nif;
	}

	public Cliente(Cliente c) {
		this.equipamentos = c.getEquipamentos();
		this.contacto = c.getContacto();
		this.nif = c.getNif();
	}

	public Map<String, Equipamento> getEquipamentos() {
		return new HashMap<>(this.equipamentos);
	}

	public void setEquipamentos(Map<String, Equipamento> equipamentos) {
		this.equipamentos = new HashMap<>(equipamentos);
	}

	public FormaContacto getContacto() {
		return new FormaContacto(contacto.clone());
	}

	public void setFormaContacto(FormaContacto fc) {
		this.contacto = new FormaContacto(fc.clone());
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String newNif) {
		this.nif = newNif;
	}

	public Equipamento getEquipamento(String equipID) throws EquipamentoNaoAssociadoAoCliente {

		if(!this.equipamentos.containsKey(equipID)) throw new EquipamentoNaoAssociadoAoCliente("Equipamento não associado ao respetivo cliente: " + this.nif + ".");
		return this.equipamentos.get(equipID);
	}

	public Cliente clone() {
		return new Cliente(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Cliente cliente = (Cliente) o;
		return Objects.equals(contacto, cliente.contacto) && Objects.equals(nif, cliente.nif);
	}

	@Override
	public int hashCode() {
		return Objects.hash(contacto, nif);
	}


}