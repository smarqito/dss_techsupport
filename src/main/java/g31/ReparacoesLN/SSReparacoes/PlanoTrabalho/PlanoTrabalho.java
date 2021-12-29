package g31.ReparacoesLN.SSReparacoes.PlanoTrabalho;

import g31.Middleware.PassoNaoExisteException;
import g31.ReparacoesLN.SSReparacoes.Reparacao.CustoTotalReparacao;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class PlanoTrabalho implements Serializable {

	private List<PassoReparacao> passos;
	private List<PassoReparacao> passosRealizados;
	private Integer tempo;

	

	public PlanoTrabalho() {
		passos = new ArrayList<>();
		passosRealizados = new ArrayList<>();
	}

	public PlanoTrabalho(List<PassoReparacao> passos) {
		this.passos = new ArrayList<>(passos);
		this.passosRealizados = new ArrayList<>(passos);
	}

	public PlanoTrabalho(PlanoTrabalho pt) {
		this.passos = pt.getPassos();
		this.passosRealizados = pt.getPassosRealizados();
		this.tempo = pt.getTempo();
	}
	/**
	 * Calcula todos os passos de reparacao
	 * @return Retorna uma copia de todos os passos de reparacao, sob a forma de lista
	 */
	public List<PassoReparacao> getPassos() {
		return passos.stream().map(PassoReparacao::clone).collect(Collectors.toList());
	}

	/**
	 * Calcula todos os passos de reparacao
	 * @return Retorna uma copia de todos os passos de reparacao, sob a forma de lista
	 */
	public List<PassoReparacao> getPassosRealizados() {
		return passosRealizados.stream().map(PassoReparacao::clone).collect(Collectors.toList());
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setPassos(List<PassoReparacao> passos) {
		this.passos = passos.stream().map(PassoReparacao::clone).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param nome
	 * @param tempo
	 * @param material
	 */
	public void addPasso(String nome, Integer tempo, Material material) {
		PassoReparacao p = new PassoReparacao(nome, tempo, material);
		passos.add(p);
		this.tempo += tempo;
	}

	/**
	 * 
	 * @param nomeP
	 * @param nomeSub
	 * @param t
	 * @param m
	 */
	public void addSubPasso(String nomeP, String nomeSub, Integer t, Material m) throws PassoNaoExisteException {
		this.getPasso(nomeP).addSubPasso(nomeSub, t, m);
		this.tempo += t;
	}

	/**
	 * 
	 * @param passo
	 */
	public void removePasso(PassoReparacao passo) {
		this.passos.remove(passo);
	}

	public void addPassoRealizado(PassoReparacao p) {
		this.removePasso(p);
		this.passosRealizados.add(p);
	}

	public boolean haMaisPassos() {
		return this.passos.isEmpty();
	}

	public List<Material> getMaterial() {
		return this.passos.stream().map(PassoReparacao::getMateriais).collect(Collectors.toList());
	}

	public CustoTotalReparacao getPrecoEfetivo(Double precoHora) {
		CustoTotalReparacao ctr = new CustoTotalReparacao(precoHora);
		for (PassoReparacao p : passosRealizados){
			p.getCusto(ctr);
		}
		return ctr;
	}

	public PassoReparacao getPassoAtual() {
		return this.passos.get(0);
	}

	/**
	 * 
	 * @param nomePasso
	 */
	public Boolean existePasso(String nomePasso) {
		return this.passos.stream().anyMatch(x -> x.getNome().equals(nomePasso));
	}

	/**
	 * 
	 * @param nomePasso
	 */
	public PassoReparacao getPasso(String nomePasso) throws PassoNaoExisteException {
		try {
			return this.passos.stream().filter(x -> x.getNome().equals(nomePasso)).findFirst().get();
		} catch (NoSuchElementException e){
			throw new PassoNaoExisteException("Passo " + nomePasso + " não existe no PlanoTrabalho.");
		}
	}

}