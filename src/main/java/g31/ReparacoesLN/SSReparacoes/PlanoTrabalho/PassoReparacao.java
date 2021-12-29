package g31.ReparacoesLN.SSReparacoes.PlanoTrabalho;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import g31.ReparacoesLN.SSReparacoes.Reparacao.CustoTotalReparacao;

public class PassoReparacao implements Serializable{

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

	public PassoReparacao() {
		this.materiais = null;
		this.subpassos = new ArrayList<>();
		this.nome = null;
		this.tempoEstimado = 0;
		this.custoEstimado = 0.0;
		this.tempoGasto = 0;
		this.custoEfetivo = 0.0;
	}

	public PassoReparacao(Material materiais, List<PassoReparacao> subpassos, String nome, Integer tempoEstimado,
			Double custoEstimado, Integer tempoGasto, Double custoEfetivo) {
		this.materiais = materiais;
		this.subpassos = subpassos;
		this.nome = nome;
		this.tempoEstimado = tempoEstimado;
		this.custoEstimado = custoEstimado;
		this.tempoGasto = tempoGasto;
		this.custoEfetivo = custoEfetivo;
	}

	public PassoReparacao(PassoReparacao pr) {
		this.materiais = pr.getMateriais();
		this.subpassos = pr.getSubpassos();
		this.nome = pr.getNome();
		this.tempoEstimado = pr.getTempoEstimado();
		this.custoEstimado = pr.getCustoEstimado();
		this.tempoGasto = pr.getTempoGasto();
		this.custoEfetivo = pr.getCustoEfetivo();
	}

	public Material getMateriais() {
		return materiais;
	}

	public void setMateriais(Material materiais) {
		this.materiais = materiais;
	}

	public List<PassoReparacao> getSubpassos() {
		return subpassos.stream().collect(Collectors.toList());
	}

	public void setSubpassos(List<PassoReparacao> subpassos) {
		this.subpassos = subpassos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getTempoEstimado() {
		return tempoEstimado;
	}

	public void setTempoEstimado(Integer tempoEstimado) {
		this.tempoEstimado = tempoEstimado;
	}

	public Double getCustoEstimado() {
		return custoEstimado;
	}

	public void setCustoEstimado(Double custoEstimado) {
		this.custoEstimado = custoEstimado;
	}

	public Integer getTempoGasto() {
		return tempoGasto;
	}

	public void setTempoGasto(Integer tempoGasto) {
		this.tempoGasto = tempoGasto;
	}

	public Double getCustoEfetivo() {
		return custoEfetivo;
	}

	public void setCustoEfetivo(Double custoEfetivo) {
		this.custoEfetivo = custoEfetivo;
	}

	/**
	 * 
	 * @param ctr
	 */
	public void getCusto(CustoTotalReparacao ctr) {
		ctr.addPasso(this.tempoGasto, this.tempoEstimado, this.custoEfetivo);
		for (PassoReparacao passoReparacao : subpassos) {
			passoReparacao.getCusto(ctr);
		}
	}

	public void addSubPasso(String nomeSub, Integer t, Material m) {
		PassoReparacao p = new PassoReparacao(nomeSub, t, m);
		this.subpassos.add(p);
	}

	public PassoReparacao clone() {
		return new PassoReparacao(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PassoReparacao that = (PassoReparacao) o;
		return Objects.equals(materiais, that.materiais) && Objects.equals(subpassos, that.subpassos)
				&& Objects.equals(nome, that.nome) && Objects.equals(tempoEstimado, that.tempoEstimado)
				&& Objects.equals(custoEstimado, that.custoEstimado) && Objects.equals(tempoGasto, that.tempoGasto)
				&& Objects.equals(custoEfetivo, that.custoEfetivo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(materiais, subpassos, nome, tempoEstimado, custoEstimado, tempoGasto, custoEfetivo);
	}

	@Override
	public String toString() {
		return "PassoReparacao{" +
				"materiais=" + materiais +
				", subpassos=" + subpassos +
				", nome='" + nome + '\'' +
				", tempoEstimado=" + tempoEstimado +
				", custoEstimado=" + custoEstimado +
				", tempoGasto=" + tempoGasto +
				", custoEfetivo=" + custoEfetivo +
				'}';
	}
}