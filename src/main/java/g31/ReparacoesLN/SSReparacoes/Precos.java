package g31.ReparacoesLN.SSReparacoes;

import java.io.Serializable;

public class Precos implements Serializable {

	private static Double horaTecnico = 25.0;

	public static Double getCustoTecnico() {
		return horaTecnico;
	}

	/**
	 * 
	 * @param novoC
	 */
	public static void setCustoTecnico(Double novoC) {
		horaTecnico = novoC;
	}

}