package g31.ReparacoesLN.SSReparacoes.Orcamento;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EstadoOrcamento implements Serializable, Comparable<EstadoOrcamento> {

	private OrcamentoEstado estado;
	private LocalDateTime data;

	/**
	 * Construtor para o Estado Orçamento que recebe o estado
	 * 
	 * @param s Enum com o estado do orçamento
	 */
	public EstadoOrcamento(OrcamentoEstado orcEstado) {
		estado = orcEstado;
		data = LocalDateTime.now();
	}

	public Boolean estaAtivo() {

		return (estado != OrcamentoEstado.arquivado);
	}

	public boolean aguardaOrcamento() {
		return estado == OrcamentoEstado.porCalcular;
	}

	public boolean enviado() {
		return estado == OrcamentoEstado.enviado;
	}
	// Getters e Setters

	public OrcamentoEstado getEstado() {
		return this.estado;
	}

	public void setEstado(OrcamentoEstado estado) {
		this.estado = estado;
	}

	public LocalDateTime getData() {
		return data;
	}

	@Override
	public String toString() {
		return "EstadoOrcamento [data=" + data + ", estado=" + estado + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstadoOrcamento other = (EstadoOrcamento) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (estado != other.estado)
			return false;
		return true;
	}

	/**
	 * Método compare para ordenar o TreeSet de Estados de Orçamento
	 */
	@Override
	public int compareTo(EstadoOrcamento arg0) {
		return arg0.data.compareTo(this.data);
	}

}