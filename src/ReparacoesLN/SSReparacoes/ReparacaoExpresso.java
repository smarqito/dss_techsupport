package ReparacoesLN.SSReparacoes;


public class ReparacaoExpresso extends Reparacao {

	private Double precoFixo;
	private Integer tempoEstimado;
	private String nome;

	/**
	 * Construtor para uma Reparação Expresso
	 * 
	 * @param precoFixo Preço da reparação
	 * @param tempoEstimado Tempo estimado para a duração da reparação
	 * @param nome Nome da reparação
	 */
	public ReparacaoExpresso(Double precoFixo, Integer tempoEstimado, String nome) {
		this.precoFixo = precoFixo;
		this.tempoEstimado = tempoEstimado;
		this.nome = nome;
	}
	
	/**
	 * Método que retorna sobre a forma da classe CustoTotalReparacao o custo da reparacao expresso1
	 * 
	 */
	@Override
	public CustoTotalReparacao getPrecoEfetivo() {
		CustoTotalReparacao custoXPresso = new CustoTotalReparacao();
		
		custoXPresso.addPasso(tempoEstimado, tempoEstimado, precoFixo);
		
		return custoXPresso;
	}
	
	/**
	 * Método para realizar a reparação expresso na sua totalidade, 
	 * dado a esta não possuir múltiplos passos
	 * 
	 */
	@Override
	public void registaPassoRealizado(Integer tempo, Double custo) {
		
		ReparacaoEstado novoEstado = ReparacaoEstado.reparado;
		
		alteraEstado(novoEstado, "");
	}

	@Override
	public String toString() {
		return "ReparacaoExpresso [nome=" + nome + ", precoFixo=" + precoFixo + ", tempoEstimado=" + tempoEstimado
				+ "]";
	}

	// Getters e Setters
	
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

	// Método equals

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
			return true;
	
		if((obj == null) || !obj.getClass().equals(this.getClass()))
			return false;
		
		ReparacaoExpresso r = (ReparacaoExpresso) obj;

		return r.getNome().equals(this.getNome()) 
			   && r.getPrecoFixo().equals(this.getPrecoFixo())
			   && r.getTempoEstimado().equals(this.getTempoEstimado());
    }
	
}