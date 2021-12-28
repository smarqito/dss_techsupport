package ReparacoesLN.SSReparacoes;

import java.util.*;

public class PlanoTrabalho {

	private List<PassoReparacao> passos;
	private Integer tempo;

    public PlanoTrabalho(List<PassoReparacao> passos) {
		this.passos = new ArrayList<>(passos);
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
	}

	/**
	 * 
	 * @param nomeP
	 * @param nomeSub
	 * @param t
	 * @param m
	 */
	public void addSubPasso(String nomeP, String nomeSub, Integer t, Material m) {
		this.getPasso(nomeP).addSubPasso(nomeSub, t, m);
	}

	/**
	 * 
	 * @param passo
	 */
	public void removePasso(PassoReparacao passo) {
		this.passos.remove(passo);
	}

	public void addPassoRealizado(PassoReparacao p) {
		// TODO
	}

	public boolean haMaisPassos() {
		// TODO
		return false;
	}

	public List<Material> getMaterial() {
		// TODO
		return null;
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
	public PassoReparacao getPasso(String nomePasso) {
		return this.passos.stream().filter(x -> x.getNome().equals(nomePasso)).findFirst().orElse(null);
	}

	public void atualizarPassoAtual() {
		//nao faz nada :)
	}

}