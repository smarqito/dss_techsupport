package Middleware;

public class NaoExisteDisponibilidadeException extends Exception {

    public NaoExisteDisponibilidadeException() {
    }

    public NaoExisteDisponibilidadeException(String message) {
        super(message);
    }
    
}
