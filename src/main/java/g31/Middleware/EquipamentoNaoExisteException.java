package g31.Middleware;

public class EquipamentoNaoExisteException extends Exception {

    public EquipamentoNaoExisteException() {
    }

    public EquipamentoNaoExisteException(String message) {
        super(message);
    }

}
