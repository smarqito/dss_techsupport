package g31.ReparacoesLN.SSReparacoes.Orcamento;

import g31.ReparacoesLN.SSClientes.*;
import g31.ReparacoesLN.SSReparacoes.Comunicacao;
import g31.ReparacoesLN.SSReparacoes.Precos;
import g31.ReparacoesLN.SSReparacoes.PlanoTrabalho.Material;
import g31.ReparacoesLN.SSReparacoes.PlanoTrabalho.PassoReparacao;
import g31.ReparacoesLN.SSReparacoes.PlanoTrabalho.PlanoTrabalho;
import g31.ReparacoesLN.SSReparacoes.Reparacao.ReparacaoProgramada;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import g31.Middleware.EstadoOrcNaoEValidoException;
import g31.Middleware.PassoNaoExisteException;

public class Orcamento implements Serializable, Comparator<Orcamento> {

	private String id;
	private Equipamento equipamento;
	private ReparacaoProgramada reparacao;
	private LocalDateTime dataCriacao;
	private LocalDateTime prazoReparacao;
	private Double preco;
	private Double custoHora;
	private String descrProb;
	private PlanoTrabalho plano;
	private Set<EstadoOrcamento> estados;
	private List<Comunicacao> comunicacoes;

	/**
	 * ID para auto incrementar
	 */
	private static int CURRENT_ID = 1;

	/**
	 * Retorna o ID atual e incrementa a variável para o próximo
	 */
	private static String GetCurrentID() {

		int curr_id = CURRENT_ID;
		up_ID();

		String id = String.valueOf(curr_id);

		return id;
	}

	/**
	 * Incrementa o variável static CURRENT_ID, para o próximo orçamento
	 */
	private static void up_ID() {
		CURRENT_ID++;
	}

	/**
	 * Construtor para criar um orçamento a partir de um equipamento e de uma
	 * descrição
	 * 
	 * @param e     Equipamento a reparar
	 * @param descr Descrição do Orçamento
	 */
	public Orcamento(Equipamento e, String descr) {
		this.id = GetCurrentID();
		this.reparacao = null;
		this.equipamento = e;
		this.dataCriacao = LocalDateTime.now();
		this.prazoReparacao = null;
		this.preco = 0.0;
		this.custoHora = Precos.getCustoTecnico();
		this.descrProb = descr;
		this.plano = new PlanoTrabalho();
		this.estados = new TreeSet<>();
		this.estados.add(new EstadoOrcamento(OrcamentoEstado.porCalcular));
		this.comunicacoes = new ArrayList<Comunicacao>();
	}

	public Orcamento(Orcamento c) {
		this.id = c.getID();
		this.equipamento = c.getEquipamento();
		this.reparacao = c.getReparacao();
		this.prazoReparacao = c.getPrazoRep();
		this.preco = c.getPreco();
		this.custoHora = c.getCustoHora();
		this.descrProb = c.getDescrProb();
		this.plano = c.getPT();
		this.estados = c.getEstados();
		this.comunicacoes = c.getComunicacoes();
		this.setDataCriacao(c.getDataCriacao());
	}

	/**
	 * Método que retorna o passo do Plano de Trabalhos associado ao Orçamento
	 * 
	 * Utiliza o método da classe PlanoTrabalho com o mesmo nome
	 * 
	 * @param nomePasso nome do passo a procurar
	 * @throws PassoNaoExisteException
	 */
	public PassoReparacao getPasso(String nomePasso) throws PassoNaoExisteException {
		return plano.getPasso(nomePasso);
	}

	/**
	 * Método que adiciona um passo no Plano de Trabalhos do orçamento
	 * 
	 * @param nome     Nome do Passo de Reparação a adicionar
	 * @param tempo    Tempo estimado para o passo
	 * @param material Material usado no passo
	 */
	public void addPasso(String nome, Integer tempo, Material material) {
		plano.addPasso(nome, tempo, material);
	}

	/**
	 * Método que adiciona um subPasso no Plano de Trabalhos do Orçamento
	 * 
	 * @param nomeP    Nome do main passo onde vai ser feita a adição
	 * @param nomeSub  Nome do subpasso a adicionar
	 * @param tempo    Tempo estimado
	 * @param material Material usado
	 * @throws PassoNaoExisteException
	 */
	public void addSubPasso(String nomeP, String nomeSub, Integer tempo, Material material)
			throws PassoNaoExisteException {
		plano.addSubPasso(nomeP, nomeSub, tempo, material);
	}

	/**
	 * Verifica se Orçamento ainda está ativo
	 * 
	 * @return true se estive ativo, false caso contrário
	 */
	public Boolean estaAtivo() {

		return getEstadoAtual().estaAtivo();
	}

	/**
	 * Altera o estado de um orçamento
	 * 
	 * Só altera o estado caso
	 * 
	 * @param novoEstado
	 * @throws EstadoOrcNaoEValidoException
	 */
	public void alteraEstado(OrcamentoEstado novoEstado) throws EstadoOrcNaoEValidoException {

		EstadoOrcamento novo = new EstadoOrcamento(novoEstado);

		if (estados.stream().noneMatch(x -> x.getEstado().equals(novoEstado))) {
			this.estados.add(novo);

		} else
			throw new EstadoOrcNaoEValidoException("Estado " + novoEstado + " não é válido!");
	}

	/**
	 * Método que retorna numa String todas as informações de um Orçamento
	 * 
	 * @return
	 */
	public String generateResume() {
		return "Orcamento [comunicacoes ="
				+ comunicacoes.stream().map(Comunicacao::toString).collect(Collectors.joining(","))
				+ ", custoHora =" + custoHora + ", descrProb =" + descrProb
				+ ", equipamento =" + equipamento + ", estado atual =" + getEstadoAtual() + ", id =" + id + ", plano ="
				+ plano
				+ ", prazoReparacao =" + prazoReparacao + ", preco =" + preco + ", reparacao =" + reparacao + "]";
	}

	/**
	 * Método que verifica se um passo exixste no plano de trabalhos do orçamento
	 * 
	 * @param nomePasso Nome do passo a procurar
	 */
	public Boolean existePasso(String nomePasso) {
		return plano.existePasso(nomePasso);
	}

	/**
	 * Método que adiciona uma comunicação efetuada com o cliente a lista de
	 * comunicações feitas
	 * 
	 * @param c Comunicação realizada
	 */
	public void addComunicacao(Comunicacao c) {
		this.comunicacoes.add(c);
	}

	/**
	 * Método que verifica se um orçamento enviado já passou o prazo estipulado para
	 * ser aceite
	 * 
	 * O prazo e de 30 dias.
	 * 
	 * @return true se passou de prazo, false caso contrário
	 */
	public Boolean passouPrazo() {

		EstadoOrcamento estadoAtual = getEstadoAtual();
		LocalDateTime tempoAtual = LocalDateTime.now();
		Boolean passouPrazo = false;

		if (estadoAtual.getEstado() == OrcamentoEstado.enviado) {
			long days = ChronoUnit.DAYS.between(estadoAtual.getData(), tempoAtual);

			if (days >= 30)
				passouPrazo = true;
		}

		return passouPrazo;
	}

	// Getters e Setters

	public String getID() {
		return id;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	private void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public ReparacaoProgramada getReparacao() {
		return reparacao;
	}

	public Integer getTempoEstimado() {
		return plano.getPrecoEfetivo(this.custoHora).getTempoTotalEstimado();
	}

	public void setReparacao(ReparacaoProgramada reparacao) {
		this.reparacao = reparacao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Double getCustoHora() {
		return custoHora;
	}

	public void setCustoHora(Double custoHora) {
		this.custoHora = custoHora;
	}

	public String getDescrProb() {
		return descrProb;
	}

	public void setDescrProb(String descrProb) {
		this.descrProb = descrProb;
	}

	public Set<EstadoOrcamento> getEstados() {
		Set<EstadoOrcamento> novoPointer = new TreeSet<>();

		estados.stream().map(x -> novoPointer.add(x));

		return novoPointer;
	}

	public List<Comunicacao> getComunicacoes() {
		return comunicacoes;
	}

	/**
	 * Método que retorna todo o material necessário para efetuar o plano de
	 * trabalhos do orçamento
	 * 
	 * @return List de materiais
	 */
	public List<Material> getMaterial() {
		// return plano.getMaterial();
		return null;
	}

	public LocalDateTime getPrazoRep() {
		return prazoReparacao;
	}

	public void setPrazoRep(LocalDateTime prazoRep) {
		prazoReparacao = prazoRep;
	}

	public PlanoTrabalho getPT() {
		return plano;
	}

	public void setPT(List<PassoReparacao> passos) {
		PlanoTrabalho pt = new PlanoTrabalho(passos);
		this.plano = pt;
	}

	public void setPT(PlanoTrabalho plano) {
		this.plano = plano;
	}

	/**
	 * Método para obter a forma de contacto do cliente cujo equipamento vai ser
	 * reparado
	 * 
	 * @return Forma de Contacto do cliente
	 */
	public FormaContacto getFormaContacto() {
		return equipamento.getProprietario().getContacto();
	}

	public EstadoOrcamento getEstadoAtual() {
		return estados.iterator().next();
	}

	// Equals

	@Override
	public boolean equals(Object obj) {

		if (obj == this)
			return true;

		if ((obj == null) || !obj.getClass().equals(this.getClass()))
			return false;

		Orcamento o = (Orcamento) obj;

		return o.getID().equals(this.getID()) &&
				o.getEquipamento().equals(this.getEquipamento()) &&
				o.getReparacao().equals(this.getReparacao()) &&
				o.getPrazoRep().equals(this.getPrazoRep()) &&
				o.getPreco().equals(this.getPreco()) &&
				o.getCustoHora().equals(this.getCustoHora()) &&
				o.getDescrProb().equals(this.getDescrProb()) &&
				o.getPT().equals(this.getPT()) &&
				o.getEstados().equals(this.getEstados()) &&
				o.getComunicacoes().equals(this.getComunicacoes());

	}

	@Override
	public int compare(Orcamento o1, Orcamento o2) {
		int res = o1.dataCriacao.compareTo(o2.dataCriacao);
		if(res == 0) {
			return o1.id.compareTo(o2.id);
		}
		return res;
	}

}