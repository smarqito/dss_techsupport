package ReparacoesLN.SSReparacoes;

import ReparacoesLN.SSClientes.EstadoEquipamento;
import ReparacoesLN.SSColaboradores.Tecnico;

public class ReparacaoProgramada extends Reparacao {

	private PlanoTrabalho plano;
	private Orcamento docOrigem;

	public ReparacaoProgramada(Orcamento docOrigem, Tecnico tec) {
		super(docOrigem.getEquipamento(), tec);
	}

	public PlanoTrabalho getPlano() {
		return new PlanoTrabalho(this.plano);
	}

	public void setPlano(PlanoTrabalho plano) {
		this.plano = new PlanoTrabalho(plano);
	}

	public Orcamento getDocOrigem() {
		return new Orcamento(this.docOrigem);
	}

	public void setDocOrigem(Orcamento docOrigem) {
		this.docOrigem = new Orcamento(docOrigem);
	}

	public ReparacaoProgramada(Orcamento docOrigem) {
		this.docOrigem = docOrigem;
		plano = docOrigem.getPT();
	}

	/**
	 * Método que regista a realização de um passo de uma reparação programada
	 * 
	 * @param tempo Tempo efetivo gasto
	 * @param custo Custo efetivo gasto
	 */
	@Override
	public void registaPassoRealizado(Integer tempo, Double custo) {

		PassoReparacao pAtual = plano.getPassoAtual();
		pAtual.setCustoEfetivo(custo);
		pAtual.setTempoGasto(tempo);
		plano.addPassoRealizado(pAtual);
		if (!plano.haMaisPassos()) {
			super.alteraEstado(ReparacaoEstado.reparado, "reparado!");
			super.getEquipamento().setEstado(EstadoEquipamento.prontoLevantar);
		}
	}

	/**
	 * Calcula o preco efetivo da reparacao, utilizando os passos realizados do
	 * plano
	 * 
	 * @return Custo total de Reparacao
	 */
	@Override
	public CustoTotalReparacao getPrecoEfetivo() {
		return plano.getPrecoEfetivo(docOrigem.getCustoHora());
	}
}