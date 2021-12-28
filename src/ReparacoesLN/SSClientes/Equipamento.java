package ReparacoesLN.SSClientes;

import java.util.*;
import ReparacoesLN.SSReparacoes.*;

public class Equipamento {

	private List<Reparacao> historicoReps;
	private List<Orcamento> historicoOrcs;
	private EstadoEquipamento estado;
	private Cliente proprietario;
	private String id;
	private String codRegisto;
	private String marca;

	//duvidas neste construtor
	public Equipamento(String codRegisto, String marca, Cliente proprietario) {
		this.historicoReps = null;
		this.historicoOrcs = null;
		this.estado = EstadoEquipamento.emProcesso;
		setProprietario(proprietario);
		this.id = null;
		this.codRegisto = codRegisto;
		this.marca = marca;
	}

	public List<Reparacao> getHistoricoReps() {
		return historicoReps;
	}

	public void setHistoricoReps(List<Reparacao> historicoReps) {
		this.historicoReps = historicoReps;
	}

	public List<Orcamento> getHistoricoOrcs() {
		return historicoOrcs;
	}

	public void setHistoricoOrcs(List<Orcamento> historicoOrcs) {
		this.historicoOrcs = historicoOrcs;
	}

	public void setEstado(EstadoEquipamento estado) {
		this.estado = estado;
	}

	public void setProprietario(Cliente proprietario) {
		this.proprietario = proprietario;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodRegisto() {
		return codRegisto;
	}

	public void setCodRegisto(String codRegisto) {
		this.codRegisto = codRegisto;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Cliente getProprietario() {
		return this.proprietario;
	}

	public FormaContacto getFormaContacto() {
		// TODO - implement Equipamento.getFormaContacto
		throw new UnsupportedOperationException();
	}

	public void getEstado() {
		// TODO - implement Equipamento.getEstado
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nif
	 */
	public void isProprietario(String nif) {
		// TODO - implement Equipamento.isProprietario
		throw new UnsupportedOperationException();
	}

}