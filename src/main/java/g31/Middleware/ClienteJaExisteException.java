package g31.Middleware;

public class ClienteJaExisteException extends Exception {

    public ClienteJaExisteException() {
    }

    public ClienteJaExisteException(String message) {
        super(message);
    }

}
