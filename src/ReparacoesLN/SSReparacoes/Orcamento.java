package ReparacoesLN.SSReparacoes;

import ReparacoesLN.SSClientes.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
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
	private TreeSet<EstadoOrcamento> estados;
	private List<Comunicacao> comunicacoes;

	/**
	 * ID para auto incrementar
	 */
	private static int CURRENT_ID = 1;

	/**
	 * Retorna o ID atual e incrementa a variável para o próximo
	 */
	private static String getCurrentID() {
		
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
		this.id = getCurrentID();
		this.reparacao = null;
		this.equipamento = e;
		this.prazoReparacao = null;
		this.preco = 0.0;
		this.custoHora = 0.0;
		this.descrProb = descr;
		this.plano = null;
		this.estados = new TreeSet<EstadoOrcamento>();
		this.estados.add(new EstadoOrcamento(OrcamentoEstado.porCalcular));
		this.comunicacoes = new ArrayList<Comunicacao>();
	}
	
	/**
	 * Método que retorna o passo do Plano de Trabalhos associado ao Orçamento
	 * 
	 * Utiliza o método da classe PlanoTrabalho com o mesmo nome
	 * 
	 * @param nomePasso nome do passo a procurar
	 */
	public PassoReparacao getPasso(String nomePasso) {
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
	 */
	public void addSubPasso(String nomeP, String nomeSub, Integer tempo, Material material) {
		plano.addSubPasso(nomeP, nomeSub, tempo, material);
	}
	
	/**
	 * Verifica se Orçamento ainda está ativo
	 * 
	 * @return true se estive ativo, false caso contrário
	 */
	public Boolean estaAtivo() {
		
		EstadoOrcamento estadoAtual = estados.first();
		Boolean isAtivo = true;

		if(estadoAtual.getEstado() == OrcamentoEstado.arquivado) {
			isAtivo = false;
		}

		return isAtivo;
	}
	
	/**
	 * 
	 * @param novoEstado
	 */
	public void alteraEstado(EstadoOrcamento novoEstado) {
		EstadoOrcamento atual = this.estados.get(0);
		if (!atual.equals(novoEstado)) {
			this.estados.add(0, novoEstado);
		}
	}
	
	/**
	 * Método que retorna numa String todas as informações de um Orçamento
	 * @return
	 */
	public String generateResume() {
		return "Orcamento [comunicacoes =" + comunicacoes + ", custoHora =" + custoHora + ", descrProb =" + descrProb
				+ ", equipamento =" + equipamento + ", estado atual =" + estados.first() + ", id =" + id + ", plano =" + plano
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
		
		EstadoOrcamento estadoAtual = estados.first();
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
	
	public TreeSet<EstadoOrcamento> getEstados() {
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
	
}