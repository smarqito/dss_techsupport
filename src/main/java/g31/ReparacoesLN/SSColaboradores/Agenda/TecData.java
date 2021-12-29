package g31.ReparacoesLN.SSColaboradores.Agenda;

import java.time.LocalDateTime;

public class TecData {

	public String tecID;
	public LocalDateTime data;

	public TecData(String id, LocalDateTime data) {
		this.tecID = id;
		this.data = data;
	}
}