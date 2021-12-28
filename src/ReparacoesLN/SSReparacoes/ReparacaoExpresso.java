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

	@Override
	public CustoTotalReparacao getPrecoEfetivo() {
		return null;
	}
	
	/**
	 * Método para realizar a reparação expresso na sua totalidade, dado a esta não possuir múltiplos passos
	 * 
	 */
	@Override
	public void registaPassoRealizado(Integer tempo, Double custo) {
		
		ReparacaoEstado novoEstado = ReparacaoEstado.reparado;

		alteraEstado(novoEstado, "");
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

	@Override
	public String toString() {
		return "ReparacaoExpresso [nome=" + nome + ", precoFixo=" + precoFixo + ", tempoEstimado=" + tempoEstimado
				+ "]";
	}

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReparacaoExpresso other = (ReparacaoExpresso) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (precoFixo == null) {
            if (other.precoFixo != null)
                return false;
        } else if (!precoFixo.equals(other.precoFixo))
            return false;
        if (tempoEstimado == null) {
            if (other.tempoEstimado != null)
                return false;
        } else if (!tempoEstimado.equals(other.tempoEstimado))
            return false;
        return true;
    }
	
}