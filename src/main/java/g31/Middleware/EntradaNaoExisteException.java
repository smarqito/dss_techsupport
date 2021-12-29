package g31.Middleware;

public class EntradaNaoExisteException extends Exception {

    public EntradaNaoExisteException() {
    }

    public EntradaNaoExisteException(String message) {
        super(message);
    }
    
}
