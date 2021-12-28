package ReparacoesLN.SSColaboradores;

import java.io.Serializable;

public abstract class Colaborador implements Serializable {

	private String colaboradorID;
	private String nome;

	public String getId() {
		return colaboradorID;
	}
}