package g31.ReparacoesLN.SSReparacoes.PlanoTrabalho;

import java.io.Serializable;
import java.util.Objects;

public class Material implements Serializable {

	private String nome;
	private double custo;
	private Integer quantidade;

	public Material(String nome, Double custo, Integer quantidade) {
		this.nome = nome;
		this.custo = custo;
		this.quantidade = quantidade;
	}

	public Material(Material m) {
		this.nome = m.getNome();
		this.custo = m.getCusto();
		this.quantidade = m.getQuantidade();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(Float custo) {
		this.custo = custo;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Material clone() {
		return new Material(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Material material = (Material) o;
		return Objects.equals(nome, material.nome) && Objects.equals(custo, material.custo) && Objects.equals(quantidade, material.quantidade);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome, custo, quantidade);
	}

	@Override
	public String toString() {
		return "Material{" +
				"nome='" + nome + '\'' +
				", custo=" + custo +
				", quantidade=" + quantidade +
				'}';
	}
}