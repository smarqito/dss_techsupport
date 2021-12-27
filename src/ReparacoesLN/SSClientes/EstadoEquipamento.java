package ReparacoesLN.SSClientes;

import java.time.LocalDateTime;

public enum EstadoEquipamento {
	abandonado,
	prontoLevantar,
	emProcesso,
	entregue;

	private LocalDateTime alterado;

}