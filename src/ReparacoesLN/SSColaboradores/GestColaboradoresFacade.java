package ReparacoesLN.SSColaboradores;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.*;

import Middleware.EntradaNaoExisteException;
import Middleware.NaoExisteDisponibilidadeException;
import Middleware.TecnicoJaTemAgendaException;
import ReparacoesLN.SSClientes.*;

public class GestColaboradoresFacade implements IGestColaboradores, Serializable {

	private Map<String, Colaborador> colabs;
	private List<Balcao> balcao;
	private GestAgenda agenda;
	private int id;

	public int getId() {
		return id++;
	}

	public void registaColaborador(String nome, String tipo) throws TecnicoJaTemAgendaException {
		Colaborador c = null;
		switch (tipo){
			case "Tecnico":
				c = new Tecnico(nome);
				agenda.addAgenda((Tecnico) c);
				break;
			case "FuncionarioBalcao":
				c = new FuncionarioBalcao(nome);
				break;
			case "Gestor":
				c = new Gestor(nome);
				break;
		}
		if (c!= null)
			colabs.put(c.getId(), c);
	}

	public Map<FuncionarioBalcao, List<Equipamento>> getEquipFuncBalcao(Class tipo, LocalDateTime de, LocalDateTime ate) {
		
		Map<FuncionarioBalcao, List<Equipamento>> balcao_equips = new HashMap<>();

		balcao.stream().forEach(b -> {

			LocalDateTime data = b.getData();

			if(data.isAfter(de) && data.isBefore(ate)) {
				FuncionarioBalcao f = b.getFuncionarioBalcao();
				Equipamento e = b.getEquipamento();

				List<Equipamento> equips = balcao_equips.get(f);

				if(equips == null) {

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

	/**
	 * 
	 * @param tecID
	 */
	public Tecnico getTecnico(String tecID) {
		// TODO - implement GestColaboradoresFacade.getTecnico
		throw new UnsupportedOperationException();
	}

	@Override
	public Boolean validaIdentificacao(String cod) {
		// TODO - implement GestColaboradoresFacade.validaIdentificacao
		throw new UnsupportedOperationException();
	}

	@Override
	public String existeDisponibilidade(Integer duracao) throws NaoExisteDisponibilidadeException {
		return agenda.temDisponibilidade(duracao);
	}

	@Override
	public LocalDateTime addEventoAgenda(String tecId, Integer tempo, String detalhes) throws NaoExisteDisponibilidadeException {
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