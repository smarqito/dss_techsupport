package Middleware;

public class ClienteJaExisteException extends Exception {

    public ClienteJaExisteException() {
    }

    public ClienteJaExisteException(String message) {
        super(message);
    }

}
