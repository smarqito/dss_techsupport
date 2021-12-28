package Middleware;

public class ReparacaoNaoExisteException extends Exception {

    public ReparacaoNaoExisteException() {
    }

    public ReparacaoNaoExisteException(String message) {
        super(message);
    }

}
