package ReparacoesLN.SSReparacoes;

import java.util.*;

public class ReparacaoProgramada extends Reparacao {

	private List<PassoReparacao> passosRealizados;
	private PlanoTrabalho plano;
	private Orcamento docOrigem;

	/**
	 * 
	 * @param tempo
	 * @param custo
	 */
	public void registaPassoRealizado(Integer tempo, Double custo) {
		// TODO - implement ReparacaoProgramada.registaPassoRealizado
		throw new UnsupportedOperationException();
	}

	@Override
	public CustoTotalReparacao getPrecoEfetivo() {
		return null;
	}
}