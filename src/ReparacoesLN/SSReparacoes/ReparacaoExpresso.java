package ReparacoesLN.SSReparacoes;

public class ReparacaoExpresso extends Reparacao {

	private Double precoFixo;
	private Integer tempoEstimado;
	private String nome;

	@Override
	public CustoTotalReparacao getPrecoEfetivo() {
		return null;
	}

	public Double getPrecoFixo() {
		return precoFixo;
	}

	public void setPrecoFixo(Double precoFixo) {
		this.precoFixo = precoFixo;
	}

	public Integer getTempoEstimado() {
		return tempoEstimado;
	}

	public void setTempoEstimado(Integer tempoEstimado) {
		this.tempoEstimado = tempoEstimado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public void registaPassoRealizado(Integer tempo, Double custo) {
		// TODO Auto-generated method stub
		
	}
	
}