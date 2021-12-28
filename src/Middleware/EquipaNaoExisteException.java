package Middleware;

public class EquipaNaoExisteException extends Exception{
    public EquipaNaoExisteException(){
        super();
    }

    public EquipaNaoExisteException(String msg){
        super(msg);
    }
}
