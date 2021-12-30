package g31.ReparacoesLN.SSReparacoes.Orcamento;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;

public class EstadoOrcamento implements Serializable ,Comparator<EstadoOrcamento> {

	private OrcamentoEstado estado;
	private LocalDateTime data;

	/**
	 * Construtor para o Estado Orçamento que recebe o estado
	 * 
	 * @param orcEstado com o estado do orçamento
	 */
	public EstadoOrcamento(OrcamentoEstado orcEstado) {
		estado = orcEstado;
		data = LocalDateTime.now();
	}
	
	/**
	 * Método compare para ordenar o TreeSet de Estados de Orçamento
	 */
	@Override
	public int compare(EstadoOrcamento o1, EstadoOrcamento o2) {
		
		LocalDateTime date1 = o1.getData();
		LocalDateTime date2 = o2.getData();

		return date2.compareTo(date1);
	}

	public Boolean estaAtivo() {

		return (estado != OrcamentoEstado.arquivado);
	}

	public Boolean foiEnviado() {
		return (estado == OrcamentoEstado.enviado);
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

}