package ReparacoesLN.SSColaboradores;

import ReparacoesLN.SSClientes.*;

import java.io.Serializable;
import java.util.Date;

public abstract class Balcao implements Serializable{

	private Equipamento equipamento;
	private FuncionarioBalcao funcionario;
	private Date data;

}