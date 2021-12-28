package ReparacoesLN.SSReparacoes;

import java.util.*;

public class PassoReparacao {

	private Material materiais;
	private List<PassoReparacao> subpassos;
	private String nome;
	private Integer tempoEstimado;
	private Double custoEstimado;
	private Integer tempoGasto;
	private Double custoEfetivo;

	public PassoReparacao(String nome, Integer tempo, Material material) {
		this.nome = nome;
		this.tempoEstimado = tempo;
		this.materiais = material;
	}

	/**
	 * 
	 * @param ctr
	 */
	public void getCusto(CustoTotalReparacao ctr) {
		// TODO - implement PassoReparacao.getCusto
		throw new UnsupportedOperationException();
	}

	public String getNome() {
		return this.nome;
	}

	public void addSubPasso(String nomeSub, Integer t, Material m) {
		PassoReparacao p = new PassoReparacao(nomeSub, t, m);
		this.subpassos.add(p);
	}
}