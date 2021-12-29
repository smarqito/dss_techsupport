package g31;

import java.io.IOException;

import g31.ReparacoesLN.IReparacoesLN;
import g31.ReparacoesLN.ReparacoesLNFacade;
import g31.ReparacoesUI.MyUI;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        IReparacoesLN model;
        try {
            model = ReparacoesLNFacade.getInstance();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Não foi possível ler o ficheiro de estado.");
            model = new ReparacoesLNFacade();
        }
        MyUI ui = new MyUI(model);
        ui.run();
        try {
            model.saveInstance();
        } catch (IOException e) {
            System.out.println("Erro a gravar");
        }
    }
}
