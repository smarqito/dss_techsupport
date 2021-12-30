package g31.ReparacoesLN;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import g31.Middleware.*;
import g31.ReparacoesBD.ReparacoesBDFacade;
import g31.ReparacoesLN.SSClientes.*;
import g31.ReparacoesLN.SSColaboradores.Balcao.Balcao;
import g31.ReparacoesLN.SSColaboradores.Balcao.Entrega;
import g31.ReparacoesLN.SSColaboradores.Balcao.Rececao;
import g31.ReparacoesLN.SSReparacoes.*;
import g31.ReparacoesLN.SSReparacoes.Orcamento.Orcamento;
import g31.ReparacoesLN.SSReparacoes.Orcamento.OrcamentoEstado;
import g31.ReparacoesLN.SSReparacoes.PlanoTrabalho.Material;
import g31.ReparacoesLN.SSReparacoes.PlanoTrabalho.PassoReparacao;
import g31.ReparacoesLN.SSReparacoes.Reparacao.CustoTotalReparacao;
import g31.ReparacoesLN.SSReparacoes.Reparacao.Reparacao;
import g31.ReparacoesLN.SSReparacoes.Reparacao.ReparacaoEstado;
import g31.ReparacoesLN.SSReparacoes.Reparacao.ReparacoesPorMes;
import g31.ReparacoesLN.SSColaboradores.*;
import g31.ReparacoesLN.SSColaboradores.Agenda.AgendaPorDia;
import g31.ReparacoesLN.SSColaboradores.Agenda.TecData;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.Colaborador;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.FuncionarioBalcao;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.Tecnico;

public class ReparacoesLNFacade implements IReparacoesLN, Serializable {

	private IGestClientes gestClientes;
	private IGestReparacoes gestReparacoes;
	private IGestColaboradores gestColaboradores;

	public ReparacoesLNFacade() {
		gestClientes = new GestClientesFacade();
		gestReparacoes = new GestReparacoesFacade();
		gestColaboradores = new GestColaboradoresFacade();
	}

	public static IReparacoesLN getInstance() throws ClassNotFoundException, IOException {
		return ReparacoesBDFacade.getState();
	}

	@Override
	public String addRepExpresso(String equipId, String nomeRepExp, String funcId)
			throws EquipamentoNaoExisteException, ReparacaoNaoExisteException, NaoExisteDisponibilidadeException,
			ColaboradorNaoTecnicoException, ColaboradorNaoExisteException, TecnicoNaoTemAgendaException,
			ColaboradorNaoFuncBalcao {

		Boolean existeRepX = gestReparacoes.existeRepXpresso(nomeRepExp);

		if (existeRepX) {
			Equipamento eq = gestClientes.getEquipamento(equipId);
			Colaborador colab = gestColaboradores.getColaborador(funcId);
			if (!colab.getClass().getSimpleName().equals(FuncionarioBalcao.class.getSimpleName())) {
				throw new ColaboradorNaoFuncBalcao();
			}
			FuncionarioBalcao fBalcao = (FuncionarioBalcao) colab;
			Balcao b = new Rececao(eq, fBalcao);

			gestColaboradores.addBalcao(b);

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
			ColaboradorNaoExisteException, NaoExisteDisponibilidadeException, TecnicoNaoTemAgendaException {
		this.gestReparacoes.alterarEstadoOrc(orcID, estado);
		if (estado.equals(OrcamentoEstado.aceite)) {
			Orcamento orc = gestReparacoes.getOrcamento(orcID);
			TecData tecDataMaisProx = gestColaboradores.prazoReparacaoMaisProx(orc.getTempoEstimado());
			orc.setPrazoRep(tecDataMaisProx.data);
			Tecnico tec = gestColaboradores.getTecnico(tecDataMaisProx.tecID);
			gestReparacoes.addReparacao(orc, tec);
			gestColaboradores.addEventoAgenda(tecDataMaisProx.tecID, orc.getTempoEstimado(),
					"Reparacao: " + orc.getID());
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
	public String registaEquipamento(String codR, String marca, String nif)
			throws ClienteNaoExisteException, EquipamentoJaAssociadoException {
		return gestClientes.registaEquipamento(codR, marca, nif);
	}

	@Override
	public void alteraEstadoEq(String equiID, EstadoEquipamento state) throws EquipamentoNaoExisteException {
		this.gestClientes.alteraEstadoEq(equiID, state);
	}

	@Override
	public void alteraEstadoEq(String equiID, EstadoEquipamento state, String funcId)
			throws EquipamentoNaoExisteException, ColaboradorNaoExisteException {
		Equipamento eq = this.getEquipamento(equiID);
		FuncionarioBalcao f = (FuncionarioBalcao) gestColaboradores.getColaborador(funcId);
		Balcao b = new Entrega(eq, f);
		gestColaboradores.addBalcao(b);
		this.gestClientes.alteraEstadoEq(equiID, state);
	}

	@Override
	public void criarPasso(String orcID, String nomePasso, String mat, Integer tempo, Integer qMat, Double custoMat)
			throws PassoJaExisteException {

		Material newMat = new Material(mat, custoMat, qMat);
		gestReparacoes.criarPasso(orcID, nomePasso, newMat, tempo);

	}

	@Override
	public String registarOrcamento(String nif, String equipId, String descr, String funcId)
			throws EquipamentoNaoExisteException, EquipamentoNaoAssociadoAoCliente, ColaboradorNaoExisteException, ColaboradorNaoFuncBalcao {
		Equipamento e = this.gestClientes.getEquipamento(equipId);
		Colaborador colab = gestColaboradores.getColaborador(funcId);
		if (!colab.getClass().getSimpleName().equals(FuncionarioBalcao.class.getSimpleName())) {
			throw new ColaboradorNaoFuncBalcao();
		}
		FuncionarioBalcao fBalcao = (FuncionarioBalcao) colab;
		Balcao b = new Rececao(e, fBalcao);
		gestColaboradores.addBalcao(b);
		if (e.isProprietario(nif)) {
			return this.gestReparacoes.registarOrcamento(e, descr);
		}
		throw new EquipamentoNaoAssociadoAoCliente();
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
	public void saveInstance() throws FileNotFoundException, IOException {
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

	@Override
	public Reparacao getReparacao(String id) throws ReparacaoNaoExisteException {
		return this.gestReparacoes.getReparacao(id);
	}

	@Override
	public AgendaPorDia getAgendaDia(LocalDate data, String tecId) throws TecnicoNaoTemAgendaException {
		return this.gestColaboradores.getAgendaDia(data, tecId);
	}

	@Override
	public Orcamento getOrcamento(String ref) throws OrcamentoNaoExisteException {
		return this.gestReparacoes.getOrcamento(ref);
	}

}