package ReparacoesLN.SSColaboradores;

import java.io.Serializable;

public abstract class Colaborador implements Serializable {

	private String colaboradorID;
	private String nome;

	/**
	 * ID para auto incrementar
	 */
	private static int CURRENT_ID = 1;

	/**
	 * Retorna o ID atual e incrementa a variável para o próximo
	 */
	private static String GetCurrentID() {
		int curr_id = CURRENT_ID++; 
		 
		String id = String.valueOf(curr_id); 
 
		return id;
	}

	/**
	 * Construtor para a classe Colaborador
	 * 
	 * @param nome Nome do colaborador em questão
	 */
	public Colaborador(String nome) {
		this.colaboradorID = GetCurrentID();
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Colaborador [colaboradorID=" + colaboradorID + ", nome=" + nome + "]";
	}

	// Getters e Setters

	public String getId() {
		return colaboradorID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	//Método equals

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if ((obj == null) || !obj.getClass().equals(this.getClass()))
			return false;

		Colaborador c = (Colaborador) obj;
		
		return c.getId().equals(this.getId()) &&
			   c.getNome().equals(this.getNome());
	}
	
	
}