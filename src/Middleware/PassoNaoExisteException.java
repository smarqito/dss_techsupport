package Middleware;

public class PassoNaoExisteException extends Exception  {
    public PassoNaoExisteException(){
        super();
    }

    public PassoNaoExisteException(String msg){
        super(msg);
    }
}
