package ReparacoesLN.SSReparacoes;

import java.util.*;

public class ReparacaoProgramada extends Reparacao {

	private PlanoTrabalho plano;
	private Orcamento docOrigem;

	
	public ReparacaoProgramada(Orcamento docOrigem) {
		this.docOrigem = docOrigem;
		plano = docOrigem.getPT();
	}

	/**
	 * Método que regista a realização de um passo de uma reparação programada
	 * 
	 * @param tempo Tempo efetivo gasto
	 * @param custo Custo efetivo gasto
	 */
	@Override
	public void registaPassoRealizado(Integer tempo, Double custo) {

		PassoReparacao pAtual;

		//pAtual = plano.getPassoAtual();

		//pAtual.setTempoEfetivo(tempo);

		//setCustoEfetivo(custo);

		//passosRealizados.add(pAtual);

		//plano.removePasso(pAtual);

	}

	@Override
	public CustoTotalReparacao getPrecoEfetivo() {
		return null;
	}
}