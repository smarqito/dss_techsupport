package ReparacoesLN.SSClientes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import ReparacoesLN.SSReparacoes.*;

public class Equipamento implements Serializable {
	private static int ID = 0;

	private static int GetID() {
		return ID++;
	}

	private List<Reparacao> historicoReps;
	private List<Orcamento> historicoOrcs;
	private EstadoEquipamento estado;
	private LocalDateTime ultimoEstado;
	private Cliente proprietario;
	private String id;
	private String codRegisto;
	private String marca;

	// duvidas neste construtor
	public Equipamento(String codRegisto, String marca, Cliente proprietario) {
		this.historicoReps = new ArrayList<>();
		this.historicoOrcs = new ArrayList<>();
		this.estado = EstadoEquipamento.emProcesso;
		this.ultimoEstado = LocalDateTime.now();
		setProprietario(proprietario);
		this.id = "" + GetID();
		this.codRegisto = codRegisto;
		this.marca = marca;
	}

	/**
	 * devolve a lista de reparacoes
	 * 
	 * @return lista de reparacoes
	 */
	public List<Reparacao> getHistoricoReps() {
		return historicoReps.stream().collect(Collectors.toList());
	}

	/**
	 * adiciona uma reparacao ao historico
	 * 
	 * @param rep Reparacao a adicionar
	 */
	public void addHistoricoReps(Reparacao rep) {
		this.historicoReps.add(rep);
	}

	/**
	 * devolve uma lista de orcamentos
	 * 
	 * @return lista de orcamentos
	 */
	public List<Orcamento> getHistoricoOrcs() {
		return historicoOrcs.stream().collect(Collectors.toList());
	}

	/**
	 * adiciona um orcamento ao historico
	 * 
	 * @param orc Orcamento a adicionar
	 */
	public void addHistoricoOrcs(Orcamento orc) {
		this.historicoOrcs.add(orc);
	}

	/**
	 * Define o estado do equipamento
	 * 
	 * @param estado
	 */
	public void setEstado(EstadoEquipamento estado) {
		this.estado = estado;
		this.ultimoEstado = LocalDateTime.now();
	}

	/**
	 * Define o proprietario do equipamento
	 * 
	 * @param proprietario Novo proprietario
	 */
	public void setProprietario(Cliente proprietario) {
		this.proprietario = proprietario;
	}

	/**
	 * retorna o ID do equipamento
	 * 
	 * @return ID do equipamento
	 */
	public String getId() {
		return id;
	}

	

	public LocalDateTime getUltimoEstado() {
		return ultimoEstado;
	}

	public String getCodRegisto() {
		return codRegisto;
	}

	public String getMarca() {
		return marca;
	}

	public Cliente getProprietario() {
		return this.proprietario;
	}

	public FormaContacto getFormaContacto() {
		return proprietario.getContacto();
	}

	public EstadoEquipamento getEstado() {
		return estado;
	}

	/**
	 *
	 * @param nif
	 * @return
	 */
	public boolean isProprietario(String nif) {
		return proprietario.getNif().equals(nif);
	}

	public void setState(EstadoEquipamento state) {
		this.estado = state;
	}

	/**
	 * Verifica se o equipamento contem um determinado orcamento, a partir do seu ID
	 * @param orcId Id do orcamento a pesquisar
	 * @return True se encontrar
	 */
	public boolean temOrcamento(String orcId) {
		return historicoOrcs.stream().anyMatch(x -> x.getID().equals(orcId));
	}

	/**
	 * Verifica se o equipamento contem um determinado reparacao, a partir do seu ID
	 * @param repId Id do reparacao a pesquisar
	 * @return True se encontrar
	 */
	public boolean temReparacao(String repId) {
		return historicoReps.stream().anyMatch(x -> x.getId().equals(repId));
	}
}