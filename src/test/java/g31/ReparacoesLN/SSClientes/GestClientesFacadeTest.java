package g31.ReparacoesLN.SSClientes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import g31.Middleware.ClienteJaExisteException;
import g31.Middleware.ClienteNaoExisteException;
import g31.Middleware.EquipamentoJaAssociadoException;
import g31.Middleware.EquipamentoNaoExisteException;

public class GestClientesFacadeTest {

    private IGestClientes gestClientes;

    public GestClientesFacadeTest() {
        this.gestClientes = new GestClientesFacade();
    }
    @Before
    public void populateGestClientes() throws ClienteJaExisteException, ClienteNaoExisteException, EquipamentoJaAssociadoException {
        for (int i = 0; i < 30; i++) {
            gestClientes.registaCliente(i+"", "0000000" + i, "email"+i+"@gmailc.om");
            gestClientes.registaEquipamento(i+"", i+"", i+"");
        }
    }

    @Test
    public void testExisteGetCliente() {
        assertTrue(gestClientes.existeCliente("5"));
    }

    @Test
    public void testFilterEquipamentos() {
        assertTrue(gestClientes.filterEquipamentos(x -> x.isProprietario("31")).isEmpty());
        assertFalse(gestClientes.filterEquipamentos(x -> x.isProprietario("5")).isEmpty());
    }

    @Test
    public void testGetEqProntoLevantar() throws EquipamentoNaoExisteException {
        assertTrue(gestClientes.getEqProntoLevantar().isEmpty());
        gestClientes.alteraEstadoEq("3", EstadoEquipamento.prontoLevantar);
        assertFalse(gestClientes.getEqProntoLevantar().isEmpty());
    }

    @Test
    public void testRegistaCliente() {
        assertThrows(ClienteJaExisteException.class, () -> gestClientes.registaCliente("2", "numero", "email"));
    }
}
