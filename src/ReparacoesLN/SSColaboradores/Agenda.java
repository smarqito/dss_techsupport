package ReparacoesLN.SSColaboradores;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Agenda {

	private List<AgendaPorDia> tarefasDia;
	private Tecnico tecnico;

	/**
	 * 
	 * @param t
	 */
	public String temDisponibilidade(Integer t) {
		if (getAgendaDia(LocalDate.now()).temDisponibilidade(t) != null){
			return getTecnicoId();
		}
		return null;
	}

	/**
	 * 
	 * @param tempo
	 * @param detalhes
	 */
	public LocalDateTime addEvento(Integer tempo, String detalhes) {
		TecData td = this.prazoMaisProx(tempo);
		return this.getAgendaDia(td.data.toLocalDate()).addEvento(tempo, detalhes);
	}

	/**
	 * 
	 * @param t
	 */
	public TecData prazoMaisProx(Integer t) {
		boolean disp = false;
		int days = 0;
		TecData td = null;
		while (!disp){
			AgendaPorDia agPd = getAgendaDia(LocalDate.now().plusDays(days++));
			LocalTime time;
			if ((time = agPd.temDisponibilidade(t)) != null){
				disp = true;
				LocalDateTime data = LocalDateTime.of(agPd.getData(), time);
				td = new TecData(getTecnicoId(), data);
			}
		}
		return td;
	}

	/**
	 * 
	 * @param data
	 */
	public void removeEvento(LocalDateTime data) {
		this.getAgendaDia(data.toLocalDate()).removeEvento(data);
	}

	/**
	 * 
	 * @param data
	 */
	public AgendaPorDia getAgendaDia(LocalDate data) {
		return tarefasDia.stream().filter(x -> x.getData().equals(data)).findFirst().orElse(null);
	}

	public String getTecnicoId() {
		return tecnico.getId();
	}

}