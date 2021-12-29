package g31.Middleware;

public class ClienteNaoExisteException extends Exception {

    public ClienteNaoExisteException() {
    }

    public ClienteNaoExisteException(String message) {
        super(message);
    }

}
