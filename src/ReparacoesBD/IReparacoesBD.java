package ReparacoesBD;

import ReparacoesLN.*;

public interface IReparacoesBD {

	ReparacoesLNFacade getState();

	/**
	 * 
	 * @param facade
	 */
	void putSate(ReparacoesLNFacade facade);

}