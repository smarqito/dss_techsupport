package g31.ReparacoesLN.SSReparacoes;

import java.util.*;

public class ReparacoesPorMes {

	private List<Reparacao> reps;
	private double duracaoMedia;
	private double desvioMedio;
	private int total = 0;

	public ReparacoesPorMes() {
		reps = new ArrayList<>();
		duracaoMedia = 0;
		desvioMedio = 0;
		total = 0;
	}

	/**
	 * Retorna a duracao media das reparacoes
	 * 
	 * @return Duracao media das reparacoes
	 */
	public Double obterDuracaoReparacoes() {
		return duracaoMedia;
	}

	/**
	 * Retorna a media do desvio entre o tempo estimado e o tempo efetivo das
	 * reparacoes
	 * 
	 * @return Media do desvio do tempo efetivo e estimado das reparacoes
	 */
	public Double obterMediaDesvio() {
		return desvioMedio;
	}

	/**
	 * Adiciona uma reparacao à lista de reparacoes
	 * Efetua o calculo da media utilizando uma media ponderada
	 * 
	 * @param rep Reparacao a ser adicionada
	 */
	public void addReparacao(Reparacao rep) {
		reps.add(rep);
		CustoTotalReparacao ctr = rep.getPrecoEfetivo();
		int tT = ctr.getTempoTotal();
		int tE = ctr.getTempoTotalEstimado();
		duracaoMedia = ((duracaoMedia * total) + tT) / (total + 1);
		desvioMedio = ((desvioMedio * total) + Math.abs(tT - tE)) / (total + 1);
		total++;
	}

}