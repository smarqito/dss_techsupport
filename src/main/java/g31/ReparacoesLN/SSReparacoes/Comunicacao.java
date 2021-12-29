package g31.ReparacoesLN.SSReparacoes;

import g31.ReparacoesLN.SSColaboradores.*;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.Colaborador;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Comunicacao implements Serializable {

	private Colaborador colaborador;
	private LocalDateTime data;
	private String msg;

	public Comunicacao(Colaborador colaborador, LocalDateTime data, String msg) {
		this.colaborador = colaborador;
		this.data = data;
		this.msg = msg;
	}

	public Comunicacao(Comunicacao c){
		this.colaborador = c.getColaborador();
		this.data = c.getData();
		this.msg = c.getMsg();
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Comunicacao clone(){
		return new Comunicacao(this);
	}
}