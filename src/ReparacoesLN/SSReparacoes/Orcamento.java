package ReparacoesLN.SSReparacoes;

import ReparacoesLN.SSClientes.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import Middleware.EstadoOrcNaoEValidoException;
import Middleware.PassoNaoExisteException;
import ReparacoesLN.SSColaboradores.*;

public class Orcamento implements Serializable {

	private String id;
	private Equipamento equipamento;
	private ReparacaoProgramada reparacao;
	private LocalDate prazoReparacao;
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
	 * Construtor para criar um orçamento a partir de um equipamento e de uma descrição
	 * 
	 * @param e Equipamento a reparar
	 * @param descr Descrição do Orçamento
	 */
	public Orcamento(Equipamento e, String descr) {
		this.id = GetCurrentID();
		this.reparacao = null;
		this.equipamento = e;
		this.prazoReparacao = null;
		this.preco = 0.0;
		this.custoHora = 0.0;
		this.descrProb = descr;
		this.plano = null;
		this.estados = new TreeSet<>();
		this.estados.add(new EstadoOrcamento(OrcamentoEstado.porCalcular));
		this.comunicacoes = new ArrayList<Comunicacao>();
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
	 * @param nome Nome do Passo de Reparação a adicionar
	 * @param tempo Tempo estimado para o passo
	 * @param material Material usado no passo
	 */
	public void addPasso(String nome, Integer tempo, Material material) {
		plano.addPasso(nome, tempo, material);
	}
	
	/**
	 * Método que adiciona um subPasso no Plano de Trabalhos do Orçamento
	 * 
	 * @param nomeP Nome do main passo onde vai ser feita a adição
	 * @param nomeSub Nome do subpasso a adicionar
	 * @param tempo Tempo estimado
	 * @param material Material usado
	 * @throws PassoNaoExisteException
	 */
	public void addSubPasso(String nomeP, String nomeSub, Integer tempo, Material material) throws PassoNaoExisteException {
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
			throw new EstadoOrcNaoEValidoException("Estado "+novoEstado+" não é válido!");
	}
	
	/**
	 * Método que retorna numa String todas as informações de um Orçamento
	 * @return
	 */
	public String generateResume() {
		return "Orcamento [comunicacoes =" + comunicacoes.stream().map(Comunicacao::toString).collect(Collectors.joining(",")) 
				+ ", custoHora =" + custoHora + ", descrProb =" + descrProb
				+ ", equipamento =" + equipamento + ", estado atual =" + getEstadoAtual() + ", id =" + id + ", plano =" + plano
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
	 * Método que adiciona uma comunicação efetuada com o cliente a lista de comunicações feitas
	 * 
	 * @param c Comunicação realizada
	 */
	public void addComunicacao(Comunicacao c) {
		this.comunicacoes.add(c);
	}
	
	/**
	 * Método que verifica se um orçamento enviado já passou o prazo estipulado para ser aceite
	 * 
	 * O prazo e de 30 dias.
	 * 
	 * @return true se passou de prazo, false caso contrário
	 */
	public Boolean passouPrazo() {
		
		EstadoOrcamento estadoAtual = getEstadoAtual();
		LocalDateTime tempoAtual = LocalDateTime.now();
		Boolean passouPrazo = false;

		if(estadoAtual.getEstado() == OrcamentoEstado.enviado) {
			long days = ChronoUnit.DAYS.between(estadoAtual.getData(), tempoAtual);

			if(days >= 30)
				passouPrazo = true;
		}

		return passouPrazo;
	}

	//Getters e Setters

	public String getID() {
		return id;
	}
	
	public Equipamento getEquipamento() {
		return equipamento;
	}
	
	public ReparacaoProgramada getReparacao() {
		return reparacao;
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
		return estados;
	}
	
	public List<Comunicacao> getComunicacoes() {
		return comunicacoes;
	}
	
	/**
	 * Método que retorna todo o material necessário para efetuar o plano de trabalhos do orçamento
	 * 
	 * @return List de materiais
	 */
	public List<Material> getMaterial() {
		//return plano.getMaterial();
		return null;
	}
	
	public LocalDate getPrazoRep() {
		return prazoReparacao;
	}

	public void setPrazoRep(LocalDate prazoRep) {
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
	 * Método para obter a forma de contacto do cliente cujo equipamento vai ser reparado
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orcamento other = (Orcamento) obj;
		if (comunicacoes == null) {
			if (other.comunicacoes != null)
				return false;
		} else if (!comunicacoes.equals(other.comunicacoes))
			return false;
		if (custoHora == null) {
			if (other.custoHora != null)
				return false;
		} else if (!custoHora.equals(other.custoHora))
			return false;
		if (descrProb == null) {
			if (other.descrProb != null)
				return false;
		} else if (!descrProb.equals(other.descrProb))
			return false;
		if (equipamento == null) {
			if (other.equipamento != null)
				return false;
		} else if (!equipamento.equals(other.equipamento))
			return false;
		if (estados == null) {
			if (other.estados != null)
				return false;
		} else if (!estados.equals(other.estados))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (plano == null) {
			if (other.plano != null)
				return false;
		} else if (!plano.equals(other.plano))
			return false;
		if (prazoReparacao == null) {
			if (other.prazoReparacao != null)
				return false;
		} else if (!prazoReparacao.equals(other.prazoReparacao))
			return false;
		if (preco == null) {
			if (other.preco != null)
				return false;
		} else if (!preco.equals(other.preco))
			return false;
		if (reparacao == null) {
			if (other.reparacao != null)
				return false;
		} else if (!reparacao.equals(other.reparacao))
			return false;
		return true;
	}
	
}