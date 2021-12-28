package Middleware;

public class ReparacaoNaoExisteException extends Exception {

    public ReparacaoNaoExisteException() {super();}

    public ReparacaoNaoExisteException(String message) {
        super(message);
    }

}
