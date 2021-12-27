package ReparacoesLN.SSReparacoes;

import java.util.*;

public class PlanoTrabalho {

	private List<PassoReparacao> passos;
	private Integer tempo;

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
	}

	/**
	 * 
	 * @param passo
	 */
	public void removePasso(PassoReparacao passo) {
		this.passos.remove(passo);
	}

	public void getPassoAtual() {
		// TODO - implement PlanoTrabalho.getPassoAtual
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nomePasso
	 */
	public Boolean existePasso(String nomePasso) {
		// TODO - implement PlanoTrabalho.existePasso
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nomePasso
	 */
	public PassoReparacao getPasso(String nomePasso) {
		// TODO - implement PlanoTrabalho.getPasso
		throw new UnsupportedOperationException();
	}

	public void atualizarPassoAtual() {
		// TODO - implement PlanoTrabalho.atualizarPassoAtual
		throw new UnsupportedOperationException();
	}

}