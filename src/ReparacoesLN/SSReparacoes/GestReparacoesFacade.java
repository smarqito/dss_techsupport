
package ReparacoesLN.SSReparacoes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Middleware.EstadoOrcNaoEValidoException;
import Middleware.OrcamentoNaoExisteException;
import Middleware.PassoJaExisteException;
import Middleware.ReparacaoExpressoJaExisteException;
import Middleware.ReparacaoJaExisteException;
import Middleware.ReparacaoNaoExisteException;
import ReparacoesLN.SSColaboradores.*;
import ReparacoesLN.SSClientes.*;

public class GestReparacoesFacade implements IGestReparacoes, Serializable {

	private Map<String, Reparacao> reps;
	private Map<String, Orcamento> orcs;
	private Set<Reparacao> repsPorData;
	private List<ReparacaoExpresso> reparacoesDisponiveis;

	public GestReparacoesFacade() {
		reps = new HashMap<>();
		orcs = new HashMap<>();
		repsPorData = new TreeSet<>();
		reparacoesDisponiveis = new ArrayList<>();
	}

	public List<Orcamento> getOrcamentosAtivos() {
		return this.orcs.values().stream().filter(Orcamento::estaAtivo).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param ref
	 * @throws OrcamentoNaoExisteException
	 */
	public Orcamento getOrcamento(String ref) throws OrcamentoNaoExisteException {
		if (orcs.containsKey(ref)) {
			return orcs.get(ref);
		}
		throw new OrcamentoNaoExisteException(ref);
	}

	/**
	 * 
	 * @param id
	 * @throws ReparacaoNaoExisteException
	 */
	public Reparacao getReparacao(String id) throws ReparacaoNaoExisteException {
		if (reps.containsKey(id)) {
			return reps.get(id);
		}
		throw new ReparacaoNaoExisteException(id);
	}

	@Override
	public List<Orcamento> filterOrcamentos(Predicate<Orcamento> p) {
		return orcs.values().stream().filter(p).collect(Collectors.toList());
	}

	@Override
	public List<Reparacao> filterReparacoes(Predicate<Reparacao> p) {
		return reps.values().stream().filter(p).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param orcID
	 * @param estado
	 * @throws EstadoOrcNaoEValidoException
	 */
	public void alterarEstadoOrc(String orcID, OrcamentoEstado estado) throws EstadoOrcNaoEValidoException {
		Orcamento o = this.orcs.get(orcID);
		o.alteraEstado(estado);
	}

	/**
	 * 
	 * @param repID
	 * @param estado
	 */
	public void alterarEstadoRep(String repID, ReparacaoEstado estado) {
		alterarEstadoRep(repID, estado, "");
	}

	/**
	 * 
	 * @param repID
	 * @param estado
	 * @param comentario
	 */
	public void alterarEstadoRep(String repID, ReparacaoEstado estado, String comentario) {
		Reparacao r = this.reps.get(repID);
		r.alteraEstado(estado, comentario);
	}

	@Override
	public Integer getTempoEstimado(String nomeRepXpresso) throws ReparacaoNaoExisteException {
		if (existeRepXpresso(nomeRepXpresso)) {
			return reparacoesDisponiveis.stream().filter(x -> x.getNome().equals(nomeRepXpresso))
					.map(y -> y.getTempoEstimado()).findFirst().get();
		}
		throw new ReparacaoNaoExisteException(nomeRepXpresso);
	}

	@Override
	public Boolean existePasso(String nomePasso) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Boolean existeCategoria(int nomeCategoria) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ReparacaoExpresso getReparacaoExpresso(String nome) throws ReparacaoNaoExisteException {
		try {
			return this.reparacoesDisponiveis.stream().filter(x -> x.getNome().equals(nome)).findFirst().get().clone();
		} catch (NoSuchElementException e) {
			throw new ReparacaoNaoExisteException(nome);
		}
	}

	@Override
	public List<ReparacaoExpresso> getReparacaoExpresso() {
		return this.reparacoesDisponiveis.stream().map(ReparacaoExpresso::clone).collect(Collectors.toList());
	}

	@Override
	public Boolean existeRepXpresso(String nome) {
		return reparacoesDisponiveis.stream().anyMatch(x -> x.getNome().equals(nome));
	}

	@Override
	public void registaCategoria(String nomeCategoria) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void registaContacto(String repId, String msg, Tecnico tec) throws ReparacaoNaoExisteException {
		Comunicacao c = new Comunicacao(tec, LocalDateTime.now(), msg);
		Reparacao r = getReparacao(repId);
		r.addComunicacao(c);
	}

	@Override
	public void registaPasso(String repID, Integer mins, Double custo) throws ReparacaoNaoExisteException {
		Reparacao rep = getReparacao(repID);
		rep.registaPassoRealizado(mins, custo);
	}

	@Override
	public void registaRepXpresso(String nome, Double preco, Integer tempo) throws ReparacaoExpressoJaExisteException {
		if (existeRepXpresso(nome)) {
			throw new ReparacaoExpressoJaExisteException("A reparação expresso " + nome + " já existe!");
		}
		ReparacaoExpresso repXpresso = new ReparacaoExpresso(preco, tempo, nome);
		reparacoesDisponiveis.add(repXpresso);
	}

	@Override
	public void registarOrcamento(Equipamento equip, String descr) {
		Orcamento o = new Orcamento(equip, descr);
		this.orcs.put(o.getID(), o);
	}

	@Override
	public void registaPT(String orcId, List<PassoReparacao> passos) {
		Orcamento o = this.orcs.get(orcId);
		o.setPT(passos);
	}

	@Override
	public void addRepExpresso(Equipamento equip, String nomeRepXpresso, Tecnico tec)
			throws ReparacaoNaoExisteException {

		ReparacaoExpresso def = getReparacaoExpresso(nomeRepXpresso);

		ReparacaoExpresso repXpresso = new ReparacaoExpresso(equip, tec, def.getPrecoFixo(), def.getTempoEstimado(),
				def.getNome());

		try {
			addReparacao(repXpresso);
		} catch (ReparacaoJaExisteException e) {
			// acabou de ser criado com novo id ... nao existe paralelismo por isso id e
			// unico
		}

	}

	private void addReparacao(Reparacao rep) throws ReparacaoJaExisteException {
		if (this.reps.containsKey(rep.getId())) {
			throw new ReparacaoJaExisteException(rep.getId());
		}
		this.reps.put(rep.getId(), rep);
	}

	@Override
	public void addReparacao(Orcamento orc, Tecnico tec) {
		ReparacaoProgramada rep = new ReparacaoProgramada(orc, tec);
		try {
			addReparacao(rep);
		} catch (ReparacaoJaExisteException e) {
			// same has before
		}
	}

	/**
	 * Calcula o preco efetivo de uma reparacao. Apenas utiliza os passos que foram
	 * realizados
	 * Se a reparacao for interrompida a meio, apenas paga o relativo ao que foi
	 * feito!
	 * 
	 * @param repID ID da reparacao a ser calculado
	 * @return Custo total Reparacao
	 * @throws ReparacaoNaoExisteException Caso a reparacao a ser calculado nao
	 *                                     exista
	 */
	public CustoTotalReparacao calcularPrecoRep(String repID) throws ReparacaoNaoExisteException {
		Reparacao rep = this.getReparacao(repID);
		return rep.getPrecoEfetivo();

	}

	public Reparacao pedidoRepMaisUrg() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void generateOrc(String orcId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void enviarOrcamento(String orcId, Colaborador col) throws OrcamentoNaoExisteException {
		Orcamento orc = getOrcamento(orcId);
		FormaContacto dest = orc.getFormaContacto();
		String msg = orc.generateResume();
		orc.addComunicacao(new Comunicacao(col, LocalDateTime.now(), msg));
		enviarEmail(msg, dest.getEmail());
	}

	@Override
	public void comunicarErro(Orcamento orc, Tecnico tec, String msg) {
		Comunicacao c = new Comunicacao(tec, LocalDateTime.now(), msg);
		orc.addComunicacao(c);
	}

	@Override
	public void enviarEmail(String msg, String dest) {
		// considerado como sendo algo externo à APP
		throw new UnsupportedOperationException();
	}

	@Override
	public void criarPasso(String orcID, String nomePasso, Material mat, Integer tempo) throws PassoJaExisteException {

		Orcamento orc = orcs.get(orcID);

		if (orc.existePasso(nomePasso)) {
			throw new PassoJaExisteException(nomePasso);
		}
		orc.addPasso(nomePasso, tempo, mat);

	}

	public void arquivarOrcamentos() {
		filterOrcamentos(x -> x.estaAtivo() && x.passouPrazo()).forEach(x -> {
			try {
				x.alteraEstado(OrcamentoEstado.arquivado);
			} catch (EstadoOrcNaoEValidoException e) {
				// nao acontece, e verificado se esta ativo!!
			}
		});
	}

	@Override
	public Map<Tecnico, ReparacoesPorMes> getReparacoesMes(LocalDateTime data) {
		Map<Tecnico, ReparacoesPorMes> ret = new HashMap<>();

		;
		for (Reparacao r : filterReparacoes(x -> x.getDataCriacao().getMonth().equals(data.getMonth())
				&& x.getDataCriacao().getYear() == data.getYear())) {
			if (!ret.containsKey(r.getTecnico()))
				ret.put(r.getTecnico(), new ReparacoesPorMes());
			ret.get(r.getTecnico()).addReparacao(r);
		}
		return ret;
	}
}