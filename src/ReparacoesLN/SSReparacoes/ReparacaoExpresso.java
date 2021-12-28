package ReparacoesLN.SSReparacoes;

public class ReparacaoExpresso extends Reparacao {

	private Double precoFixo;
	private Integer tempoEstimado;
	private String nome;

	@Override
	public CustoTotalReparacao getPrecoEfetivo() {
		return null;
	}
}