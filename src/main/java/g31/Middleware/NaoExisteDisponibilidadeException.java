package g31.Middleware;

public class NaoExisteDisponibilidadeException extends Exception {

    public NaoExisteDisponibilidadeException() {
    }

    public NaoExisteDisponibilidadeException(String message) {
        super(message);
    }
    
}
