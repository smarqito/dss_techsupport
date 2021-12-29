package g31.ReparacoesBD;

import g31.ReparacoesLN.*;

public interface IReparacoesBD {

	ReparacoesLNFacade getState();

	/**
	 * 
	 * @param facade
	 */
	void putSate(ReparacoesLNFacade facade);

}