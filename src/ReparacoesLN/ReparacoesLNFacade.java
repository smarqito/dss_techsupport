package ReparacoesLN;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Middleware.ClienteJaExisteException;
import Middleware.ClienteNaoExisteException;
import Middleware.ColaboradorNaoExisteException;
import Middleware.ColaboradorNaoTecnicoException;
import Middleware.EquipamentoJaAssociadoException;
import Middleware.EquipamentoNaoExisteException;
import Middleware.EstadoOrcNaoEValidoException;
import Middleware.NaoExisteDisponibilidadeException;
import Middleware.NaoExisteOrcamentosAtivosException;
import Middleware.OrcamentoNaoExisteException;
import Middleware.PassoJaExisteException;
import Middleware.ReparacaoExpressoJaExisteException;
import Middleware.ReparacaoNaoExisteException;
import Middleware.TecnicoJaTemAgendaException;
import Middleware.TipoColaboradorErradoException;
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
	public String addRepExpresso(String equipId, String nomeRepExp)
			throws EquipamentoNaoExisteException, ReparacaoNaoExisteException, NaoExisteDisponibilidadeException,
			ColaboradorNaoTecnicoException, ColaboradorNaoExisteException {

		Boolean existeRepX = gestReparacoes.existeRepXpresso(nomeRepExp);

		if (existeRepX) {

			Equipamento eq = gestClientes.getEquipamento(equipId);

			Integer duracao_estimada = gestReparacoes.getTempoEstimado(nomeRepExp);

			String tecId = gestColaboradores.existeDisponibilidade(duracao_estimada);

			String id = gestReparacoes.addRepExpresso(eq, nomeRepExp, gestColaboradores.getTecnico(tecId));
			gestColaboradores.addEventoAgenda(tecId, duracao_estimada, "repExpresso: " + id);
			return id;
		}
		throw new ReparacaoNaoExisteException(nomeRepExp);
	}

	@Override
	public void enviarOrcamento(String orcId, String colabId)
			throws ColaboradorNaoExisteException, OrcamentoNaoExisteException {
		Colaborador colab = gestColaboradores.getColaborador(colabId);
		this.gestReparacoes.enviarOrcamento(orcId, colab);
	}

	@Override
	public Set<Orcamento> getOrcamentosAtivos() {
		return this.gestReparacoes.getOrcamentosAtivos();
	}

	@Override
	public void alterarEstadoOrc(String orcID, OrcamentoEstado estado)
			throws EstadoOrcNaoEValidoException, OrcamentoNaoExisteException, ColaboradorNaoTecnicoException,
			ColaboradorNaoExisteException {
		this.gestReparacoes.alterarEstadoOrc(orcID, estado);
		if (estado.equals(OrcamentoEstado.aceite)) {
			Orcamento orc = gestReparacoes.getOrcamento(orcID);
			TecData tecDataMaisProx = gestColaboradores.prazoReparacaoMaisProx(orc.getTempoEstimado());
			orc.setPrazoRep(tecDataMaisProx.data);
			Tecnico tec = gestColaboradores.getTecnico(tecDataMaisProx.tecID);
			gestReparacoes.addReparacao(orc, tec);
		}
	}

	@Override
	public void alterarEstadoRep(String repID, ReparacaoEstado estado) throws ReparacaoNaoExisteException {
		this.gestReparacoes.alterarEstadoRep(repID, estado);
	}

	@Override
	public void alterarEstadoRep(String repID, ReparacaoEstado estado, String comentario)
			throws ReparacaoNaoExisteException {
		this.gestReparacoes.alterarEstadoRep(repID, estado, comentario);
	}

	@Override
	public CustoTotalReparacao calcularPrecoRep(String repID) throws ReparacaoNaoExisteException {
		return gestReparacoes.calcularPrecoRep(repID);
	}

	@Override
	public void generateOrc(String orcId) {
		gestReparacoes.generateOrc(orcId);
	}

	@Override
	public void registaPasso(String repID, Integer mins, Double custo) throws ReparacaoNaoExisteException {

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
	public void criarPasso(String orcID, String nomePasso, String mat, Integer tempo, Integer qMat, Double custoMat)
			throws PassoJaExisteException {

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
	public void registaContacto(String repID, String tecID, String msg)
			throws ReparacaoNaoExisteException, ColaboradorNaoTecnicoException, ColaboradorNaoExisteException {
		Tecnico tec = this.gestColaboradores.getTecnico(tecID);
		this.gestReparacoes.registaContacto(repID, msg, tec);
	}

	@Override
	public void registaColaborador(String nome, Class<? extends Colaborador> tipo)
			throws TecnicoJaTemAgendaException, TipoColaboradorErradoException {
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
	public void registaPT(String orcId, List<PassoReparacao> passos) throws OrcamentoNaoExisteException {
		this.gestReparacoes.registaPT(orcId, passos);
	}

	@Override
	public void comunicarErro(String orcId, String msg, String tecID)
			throws OrcamentoNaoExisteException, ColaboradorNaoTecnicoException, ColaboradorNaoExisteException {
		Tecnico tec = this.gestColaboradores.getTecnico(tecID);
		Orcamento orc = this.gestReparacoes.getOrcamento(orcId);
		this.gestReparacoes.comunicarErro(orc, tec, msg);
	}

	@Override
	public Map<Tecnico, ReparacoesPorMes> getReparacoesMes(LocalDateTime data) {
		return gestReparacoes.getReparacoesMes(data);
	}

	@Override
	public void registarRepExpresso(String nome, Integer tempo, Double custo)
			throws ReparacaoExpressoJaExisteException {
		gestReparacoes.registaRepXpresso(nome, custo, tempo);
	}

	@Override
	public Orcamento getOrcamentoMaisAntigo() throws NaoExisteOrcamentosAtivosException {
		return gestReparacoes.getOrcamentoMaisAntigo();
	}
	

	@Override
	public List<Orcamento> arquivaOrcamentos() {
		return gestReparacoes.arquivarOrcamentos();
	}

	@Override
	public List<Equipamento> darBaixaEquipamentos() {
		return gestClientes.darBaixaEquipamentos();
	}

	@Override
	public boolean existeColaborador(String id) {
		return this.gestColaboradores.existeColaborador(id);
	}

	@Override
	public Colaborador getColaborador(String id) throws ColaboradorNaoExisteException {
		return this.gestColaboradores.getColaborador(id);
	}

}