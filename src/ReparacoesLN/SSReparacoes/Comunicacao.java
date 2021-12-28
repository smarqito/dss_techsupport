package ReparacoesLN.SSReparacoes;

import ReparacoesLN.SSColaboradores.*;

import java.time.LocalDateTime;

public class Comunicacao {

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