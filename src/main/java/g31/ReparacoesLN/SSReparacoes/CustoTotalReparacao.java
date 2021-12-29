package g31.ReparacoesLN.SSReparacoes;

public class CustoTotalReparacao {

	private int tempoTotal;
	private double custoMaterial;
	private int tempoTotalEstimado;
	private double precoHora;

	public CustoTotalReparacao(Double precoHora) {
		tempoTotal = 0;
		custoMaterial = 0;
		tempoTotalEstimado = 0;
		this.precoHora = precoHora;
	}

	/**
	 * 
	 * @param tempo
	 * @param tempEstimado
	 * @param custoMaterial
	 */
	public void addPasso(Integer tempo, Integer tempEstimado, Double custoMaterial) {
		tempoTotal += tempo;
		this.custoMaterial += custoMaterial;
		tempoTotalEstimado += tempEstimado;
	}

	public Integer getTempoTotal() {
		return this.tempoTotal;
	}

	public Double getCustoMaterial() {
		return this.custoMaterial;
	}

	public int getTempoTotalEstimado() {
		return this.tempoTotalEstimado;
	}

	public double getCustoTotal() {
		return (precoHora * (tempoTotal / 60)) + custoMaterial;
	}

}