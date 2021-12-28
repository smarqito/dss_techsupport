package ReparacoesLN.SSReparacoes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;

public class EstadoOrcamento implements Serializable ,Comparator<EstadoOrcamento> {

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
	
	/**
	 * Método compare para ordenar o TreeSet de Estados de Orçamento
	 */
	@Override
	public int compare(EstadoOrcamento o1, EstadoOrcamento o2) {
		
		if(o2.data.isAfter(o1.data))
			return 1;

		else return 0;
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

	public void setData(LocalDateTime data) {
		this.data = data;
	}

}