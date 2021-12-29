package g31.ReparacoesBD;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import g31.ReparacoesLN.*;

public class ReparacoesBDFacade {
	public static String Filename = "state.obj";

	public static IReparacoesLN getState() throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(Filename);
		ObjectInputStream ois = new ObjectInputStream(fis);
		IReparacoesLN rln = (ReparacoesLNFacade) ois.readObject();
		ois.close();
		return rln;
	}

	/**
	 * 
	 * @param facade
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void putSate(ReparacoesLNFacade facade) throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Filename));
		oos.writeObject(facade);
		oos.close();
	}

}