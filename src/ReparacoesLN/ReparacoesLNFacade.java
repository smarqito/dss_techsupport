package ReparacoesLN;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import Middleware.ClienteJaExisteException;
import Middleware.ClienteNaoExisteException;
import Middleware.EquipamentoJaAssociadoException;
import Middleware.EquipamentoNaoExisteException;
import Middleware.EstadoOrcNaoEValidoException;
import Middleware.ReparacaoExpressoJaExisteException;
import Middleware.ReparacaoNaoExisteException;
import Middleware.TecnicoJaTemAgendaException;
import ReparacoesBD.ReparacoesBDFacade;
import ReparacoesLN.SSClientes.*;
import ReparacoesLN.SSReparacoes.*;
import ReparacoesLN.SSColaboradores.*;

public class ReparacoesLNFacade implements IReparacoesLN, Serializable {

	private IGestClientes gestClientes;
	private IGestReparacoes gestReparacoes;
	private IGestColaboradores gestColaboradores;

	public static ReparacoesLNFacade getInstance() {
		return ReparacoesBDFacade.getState();
	}

	@Override
	public void addRepExpresso(String equipId, String nomeRepExp) throws EquipamentoNaoExisteException {

		Boolean existeRepX = gestReparacoes.existeRepXpresso(nomeRepExp);

		if (existeRepX) {

			Equipamento eq = gestClientes.getEquipamento(equipId);

			Integer duracao_estimada = gestReparacoes.getTempoEstimado(nomeRepExp);

			/**
			 * try {
			 * 
			 * String tecID = gestColaboradores.existeDisponibilidade(duracao_estimada);
			 * Tecnico tecnico = gestColaboradores.getTecnico(tecID);
			 * gestReparacoes.addRepExpresso(eq, nomeRepExp, tecnico);
			 * 
			 * } catch (NaoExisteDisponibilidadeException e) {
			 * 
			 * throw new NaoExisteDisponibilidadeException("Não existem técnicos disponíveis
			 * para efetuar "+nomeRepExp+" no equipamento "+equipId);
			 * }
			 * 
			 * } else throw new ReparacaoXPressoNaoExisteException("A reparacao
			 * "+nomeRepExp+" não existe!");
			 */
		}
	}

	@Override
	public void enviarOrcamento(String orcId) {
		this.gestReparacoes.enviarOrcamento(orcId);
	}

	@Override
	public List<Orcamento> getOrcamentosAtivos() {
		return this.gestReparacoes.getOrcamentosAtivos();
	}

	@Override
	public void alterarEstadoOrc(String orcID, OrcamentoEstado estado) throws EstadoOrcNaoEValidoException {
		this.gestReparacoes.alterarEstadoOrc(orcID, estado);
	}

	@Override
	public void alterarEstadoRep(String repID, ReparacaoEstado estado) {
		this.gestReparacoes.alterarEstadoRep(repID, estado);
	}

	@Override
	public void alterarEstadoRep(String repID, ReparacaoEstado estado, String comentario) {
		this.gestReparacoes.alterarEstadoRep(repID, estado, comentario);
	}

	@Override
	public CustoTotalReparacao calcularPrecoRep(String repID) throws ReparacaoNaoExisteException {
		return gestReparacoes.calcularPrecoRep(repID);
	}

	@Override
	public void generateOrc(String orcId) {
		Orcamento o = gestReparacoes.getOrcamento(orcId);
	}

	@Override
	public void registaPasso(String repID, Integer mins, Double custo) {

		gestReparacoes.registaPasso(repID, mins, custo);
	}

	@Override
	public Cliente getCliente(String nif) throws ClienteNaoExisteException {
		return gestClientes.getCliente(nif);
	}

	@Override
	public Equipamento getEquipamento(String equipID) throws EquipamentoNaoExisteException {
		return gestClientes.getEquipamento(equipID);
	}

	@Override
	public Equipamento getEquipamento(String codR, String marca) throws EquipamentoNaoExisteException {
		return this.gestClientes.getEquipamento(codR, marca);
	}

	@Override
	public Boolean existeCliente(String nif) {
		try {
			this.getCliente(nif);
		} catch (ClienteNaoExisteException e) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean existeEquipamento(String codR, String marca) {
		return gestClientes.existeEquipamento(codR, marca);
	}

	@Override
	public List<Equipamento> getEqProntoLevantar() {
		return this.gestClientes.getEqProntoLevantar();
	}

	@Override
	public void registaCliente(String nif, String numero, String email) throws ClienteJaExisteException {
		this.gestClientes.registaCliente(nif, numero, email);
	}

	@Override
	public void registaEquipamento(String codR, String marca, String nif)
			throws ClienteNaoExisteException, EquipamentoJaAssociadoException {
		gestClientes.registaEquipamento(codR, marca, nif);
	}

	@Override
	public void alteraEstadoEq(String equiID, EstadoEquipamento state) throws EquipamentoNaoExisteException {
		this.gestClientes.alteraEstadoEq(equiID, state);
	}

	@Override
	public void criarPasso(String orcID, String nomePasso, String mat, Integer tempo, Integer qMat, Double custoMat) {

		Material newMat = new Material(null, mat, custoMat, qMat);
		gestReparacoes.criarPasso(orcID, nomePasso, newMat, tempo);

	}

	@Override
	public void registarOrcamento(String nif, String equipId, String descr) throws EquipamentoNaoExisteException {
		Equipamento e = this.gestClientes.getEquipamento(equipId);
		if (e.isProprietario(nif)) {
			this.gestReparacoes.registarOrcamento(e, descr);
		}
	}

	@Override
	public void registaContacto(String repID, String tecID, String msg) throws ReparacaoNaoExisteException {
		Tecnico tec = this.gestColaboradores.getTecnico(tecID);
		this.gestReparacoes.registaContacto(repID, msg, tec);
	}

	@Override
	public void registaColaborador(String nome, String tipo) throws TecnicoJaTemAgendaException {
		this.gestColaboradores.registaColaborador(nome, tipo);
	}

	@Override
	public Map<FuncionarioBalcao, List<Equipamento>> getEquipRecebidos(LocalDateTime de, LocalDateTime ate) {
		return this.gestColaboradores.getEquipRecebidos(de, ate);
	}

	@Override
	public Map<FuncionarioBalcao, List<Equipamento>> getEquipEntregue(LocalDateTime de, LocalDateTime ate) {
		return this.gestColaboradores.getEquipEntregue(de, ate);
	}

	@Override
	public void saveInstance() {
		ReparacoesBDFacade.putSate(this);
	}

	@Override
	public void registaPT(String orcId, List<PassoReparacao> passos) {
		this.gestReparacoes.registaPT(orcId, passos);
	}

	@Override
	public void comunicarErro(String orcId, String msg, String tecID) {
		Tecnico tec = this.gestColaboradores.getTecnico(tecID);
		Orcamento orc = this.gestReparacoes.getOrcamento(orcId);
		this.gestReparacoes.comunicarErro(orc, tec, msg);
	}

	@Override
	public Map<Tecnico, List<ReparacoesPorMes>> getReparacoesMes(LocalDateTime data) {
		// TODO - implement ReparacoesLNFacade.getReparacoesMes
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void registarRepExpresso(String nome, Integer tempo, Double custo) throws ReparacaoExpressoJaExisteException {
		gestReparacoes.registaRepXpresso(nome, custo, tempo);
	}

}