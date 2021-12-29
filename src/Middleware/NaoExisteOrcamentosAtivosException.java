package Middleware;

public class NaoExisteOrcamentosAtivosException extends Exception {

    public NaoExisteOrcamentosAtivosException() {
    }

    public NaoExisteOrcamentosAtivosException(String message) {
        super(message);
    }
    
}
