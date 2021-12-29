package g31;

import g31.ReparacoesLN.IReparacoesLN;
import g31.ReparacoesLN.ReparacoesLNFacade;
import g31.ReparacoesUI.MyUI;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        IReparacoesLN model = ReparacoesLNFacade.getInstance();
        MyUI ui = new MyUI(model);
        ui.run();
    }
}
