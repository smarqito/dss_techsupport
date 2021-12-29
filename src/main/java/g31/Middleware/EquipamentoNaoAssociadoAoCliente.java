package g31.Middleware;

public class EquipamentoNaoAssociadoAoCliente extends Exception {

    public EquipamentoNaoAssociadoAoCliente() {
        super();
    }

    public EquipamentoNaoAssociadoAoCliente(String m) {
        super(m);
    }

    public EquipamentoNaoAssociadoAoCliente(EquipamentoNaoAssociadoAoCliente ex) {
        super(ex);
    }
}
