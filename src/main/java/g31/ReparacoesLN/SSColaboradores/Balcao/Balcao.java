package g31.ReparacoesLN.SSColaboradores.Balcao;

import g31.ReparacoesLN.SSClientes.*;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.FuncionarioBalcao;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Balcao implements Serializable {

	private Equipamento equipamento;
	private FuncionarioBalcao funcionario;
	private LocalDateTime data;

	public Balcao(Equipamento equipamento, FuncionarioBalcao funcionario) {
		this.equipamento = equipamento;
		this.funcionario = funcionario;
		this.data = LocalDateTime.now();
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public FuncionarioBalcao getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioBalcao funcionario) {
		this.funcionario = funcionario;
	}

	public LocalDateTime getData() {
		return data;
	}

}