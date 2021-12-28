package Middleware;

public class OrcamentoNaoExisteException extends Exception{
    public OrcamentoNaoExisteException() {super();}

    public OrcamentoNaoExisteException(String message) {
        super(message);
    }
}
