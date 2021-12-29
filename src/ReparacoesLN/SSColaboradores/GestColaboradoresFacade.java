package ReparacoesLN.SSColaboradores;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.*;

import Middleware.ColaboradorNaoExisteException;
import Middleware.ColaboradorNaoTecnicoException;
import Middleware.EntradaNaoExisteException;
import Middleware.NaoExisteDisponibilidadeException;
import Middleware.TecnicoJaTemAgendaException;
import Middleware.TipoColaboradorErradoException;
import ReparacoesLN.SSClientes.*;

public class GestColaboradoresFacade implements IGestColaboradores, Serializable {

	private Map<String, Colaborador> colabs;
	private List<Balcao> balcao;
	private GestAgenda agenda;

	@Override
	public String registaColaborador(String nome, Class<? extends Colaborador> tipo)
			throws TecnicoJaTemAgendaException, TipoColaboradorErradoException {
		var params = new Class[1];
		params[0] = String.class;
		try {
			Colaborador c = tipo.getDeclaredConstructor(params).newInstance(nome);
			colabs.put(c.getId(), c);
			if (tipo.getSimpleName().equals(Tecnico.class.getSimpleName())) {
				agenda.addAgenda((Tecnico) c);
			}
			return c.getId();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new TipoColaboradorErradoException(tipo.toString());
		}
	}

	@Override
	public Map<FuncionarioBalcao, List<Equipamento>> getEquipFuncBalcao(Class<? extends Balcao> tipo, LocalDateTime de,
			LocalDateTime ate) {

		Map<FuncionarioBalcao, List<Equipamento>> balcao_equips = new HashMap<>();

		balcao.stream().forEach(b -> {

			LocalDateTime data = b.getData();

			if (data.isAfter(de) && data.isBefore(ate)) {
				FuncionarioBalcao f = b.getFuncionario();
				Equipamento e = b.getEquipamento();

				List<Equipamento> equips = balcao_equips.get(f);

				if (equips == null) {

					List<Equipamento> novo = new ArrayList<>();

					novo.add(e);

					balcao_equips.put(f, novo);

				} else {

					equips.add(e);

					balcao_equips.replace(f, equips);
				}
			}
		});

		return balcao_equips;
	}

	public Map<FuncionarioBalcao, List<Equipamento>> getEquipRecebidos(LocalDateTime de, LocalDateTime ate) {

		return getEquipFuncBalcao(Rececao.class, de, ate);
	}

	public Map<FuncionarioBalcao, List<Equipamento>> getEquipEntregue(LocalDateTime de, LocalDateTime ate) {

		return getEquipFuncBalcao(Entrega.class, de, ate);
	}

	@Override
	public Colaborador getColaborador(String colabId) throws ColaboradorNaoExisteException {
		if (this.colabs.containsKey(colabId)) {
			return this.colabs.get(colabId);
		}
		throw new ColaboradorNaoExisteException();
	}

	@Override
	public Tecnico getTecnico(String tecID) throws ColaboradorNaoTecnicoException, ColaboradorNaoExisteException {
		Colaborador colab = getColaborador(tecID);
		if (colab.getClass().getSimpleName().equals(Tecnico.class.getSimpleName())) {
			return (Tecnico) colab;
		} else {
			throw new ColaboradorNaoTecnicoException(tecID);
		}
	}

	@Override
	public Boolean validaIdentificacao(String cod) {
		return this.colabs.containsKey(cod);
	}

	@Override
	public String existeDisponibilidade(Integer duracao) throws NaoExisteDisponibilidadeException {
		return agenda.temDisponibilidade(duracao);
	}

	@Override
	public LocalDateTime addEventoAgenda(String tecId, Integer tempo, String detalhes)
			throws NaoExisteDisponibilidadeException {
		return this.agenda.addEvento(tecId, tempo, detalhes);
	}

	@Override
	public LocalDateTime addEventoAgenda(Integer tempo, String detalhes) throws NaoExisteDisponibilidadeException {
		return this.agenda.addEvento(tempo, detalhes);
	}

	@Override
	public void removeEventoAgenda(String tecId, LocalDateTime data) throws EntradaNaoExisteException {
		agenda.removeEvento(tecId, data);
	}

	@Override
	public TecData prazoReparacaoMaisProx(Integer duracao) {
		return this.agenda.prazoMaisProx(duracao);
	}

}