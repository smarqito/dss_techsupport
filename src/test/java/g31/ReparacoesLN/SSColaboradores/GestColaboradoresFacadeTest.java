package g31.ReparacoesLN.SSColaboradores;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;

import g31.Middleware.ColaboradorNaoExisteException;
import g31.Middleware.ColaboradorNaoTecnicoException;
import g31.Middleware.EntradaNaoExisteException;
import g31.Middleware.NaoExisteDisponibilidadeException;
import g31.Middleware.TecnicoJaTemAgendaException;
import g31.Middleware.TecnicoNaoTemAgendaException;
import g31.Middleware.TipoColaboradorErradoException;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.Gestor;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.Tecnico;

public class GestColaboradoresFacadeTest {

    private IGestColaboradores gestColaboradores;

    public GestColaboradoresFacadeTest() {
        gestColaboradores = new GestColaboradoresFacade();
    }

    @Test
    public void testAddEventoAgenda() throws TecnicoJaTemAgendaException, TipoColaboradorErradoException,
            NaoExisteDisponibilidadeException, TecnicoNaoTemAgendaException {
        assertThrows(NaoExisteDisponibilidadeException.class, () -> gestColaboradores.addEventoAgenda(30, "detalhes"));
        gestColaboradores.registaColaborador("Grupo31", Tecnico.class);
        gestColaboradores.addEventoAgenda(30, "detalhes");
        gestColaboradores.addEventoAgenda(30, "detalhes");
        gestColaboradores.addEventoAgenda(30, "detalhes");
        gestColaboradores.addEventoAgenda(30, "detalhes");
        assertTrue(gestColaboradores.getAgendaDia(LocalDate.now(), "1").getEntradaAgenda().size() == 0);
        assertTrue(gestColaboradores.getAgendaDia(LocalDate.now().plusDays(1), "1").getEntradaAgenda().size() == 4);
    }

    @Test
    public void testExisteColaborador() throws TecnicoJaTemAgendaException, TipoColaboradorErradoException {
        assertThrows(ColaboradorNaoExisteException.class, () -> gestColaboradores.getColaborador("cod"));
        gestColaboradores.registaColaborador("Grupo31", Tecnico.class);
        assertTrue(gestColaboradores.existeColaborador("1"));
    }

    @Test
    public void testExisteDisponibilidade() throws TecnicoJaTemAgendaException, TipoColaboradorErradoException,
            NaoExisteDisponibilidadeException, TecnicoNaoTemAgendaException, EntradaNaoExisteException {
        gestColaboradores.registaColaborador("Grupo31", Tecnico.class);
        gestColaboradores.registaColaborador("Grupo31", Tecnico.class);
        assertThrows(NaoExisteDisponibilidadeException.class, () -> gestColaboradores.existeDisponibilidade(40));
        gestColaboradores.addEventoAgenda(30, "detalhes");
        assertTrue(gestColaboradores.getAgendaDia(LocalDate.now().plusDays(1), "1").getEntradaAgenda().size() == 1);
        LocalDateTime adicionado = gestColaboradores.addEventoAgenda(30, "detalhes");
        assertTrue(gestColaboradores.getAgendaDia(LocalDate.now().plusDays(1), "2").getEntradaAgenda().size() == 1);
        gestColaboradores.removeEventoAgenda("2", adicionado.minusMinutes(30));
        assertThrows(EntradaNaoExisteException.class, () -> gestColaboradores.removeEventoAgenda("1", LocalDateTime.now().minusDays(1)));
        assertFalse(gestColaboradores.getAgendaDia(LocalDate.now().plusDays(1), "2").getEntradaAgenda().size() == 1);
    }


    @Test
    public void testGetEquipEntregueRecebidos() {
        // testa 
    }


    @Test
    public void testGetTecnico() throws TecnicoJaTemAgendaException, TipoColaboradorErradoException {
        gestColaboradores.registaColaborador("Grupo31", Tecnico.class);
        gestColaboradores.registaColaborador("Grupo31", Tecnico.class);
        gestColaboradores.registaColaborador("Grupo31", Gestor.class);
        assertThrows(ColaboradorNaoTecnicoException.class, () -> gestColaboradores.getTecnico("3"));

    }
}
