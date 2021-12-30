package g31.ReparacoesLN;

import g31.Middleware.ClienteJaExisteException;
import g31.Middleware.ClienteNaoExisteException;
import g31.Middleware.ColaboradorNaoExisteException;
import g31.Middleware.ColaboradorNaoFuncBalcao;
import g31.Middleware.ColaboradorNaoTecnicoException;
import g31.Middleware.EquipamentoJaAssociadoException;
import g31.Middleware.EquipamentoNaoAssociadoAoCliente;
import g31.Middleware.EquipamentoNaoExisteException;
import g31.Middleware.NaoExisteDisponibilidadeException;
import g31.Middleware.ReparacaoExpressoJaExisteException;
import g31.Middleware.ReparacaoNaoExisteException;
import g31.Middleware.TecnicoJaTemAgendaException;
import g31.Middleware.TecnicoNaoTemAgendaException;
import g31.Middleware.TipoColaboradorErradoException;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.FuncionarioBalcao;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.Gestor;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.Tecnico;

public class Seed {
    public static void seed(IReparacoesLN model) {
        seed_clientes_equipamentos(model);
        seed_colaboradores(model);
        seed_orcamentos(model);
        seed_rep_expresso(model);
    }

    public static void seed_colaboradores(IReparacoesLN model) {
        try {
            model.registaColaborador("Marco", Tecnico.class); // 1
            model.registaColaborador("Jose", Tecnico.class); // 2
            model.registaColaborador("Miguel", Tecnico.class); // 3
            model.registaColaborador("Mariana", Tecnico.class); // 4
            model.registaColaborador("Oscar", Gestor.class); // 5
            model.registaColaborador("Creissac", Gestor.class); // 6
            model.registaColaborador("Gualtar", FuncionarioBalcao.class); // 7
            model.registaColaborador("Guimaraes", FuncionarioBalcao.class); // 8
        } catch (TecnicoJaTemAgendaException | TipoColaboradorErradoException e) {
        }

    }

    public static void seed_clientes_equipamentos(IReparacoesLN model) {
        for (int i = 1; i < 100; i++) {
            try {
                model.registaCliente(i + "", i + "", "a" + i + "@alunos.uminho.pt");
                model.registaEquipamento(i + "", "POO", i + "");
            } catch (ClienteJaExisteException | ClienteNaoExisteException | EquipamentoJaAssociadoException e) {
            }
        }
    }

    public static void seed_orcamentos(IReparacoesLN model) {
        for (int i = 1; i < 60; i++) {
            try {
                model.registarOrcamento(i + "", i + "", "Problema com " + i, ((i % 2) == 0 ? 7 : 8) + "");
            } catch (EquipamentoNaoExisteException | EquipamentoNaoAssociadoAoCliente
                    | ColaboradorNaoExisteException | ColaboradorNaoFuncBalcao e) {
            }
        }
    }

    public static void seed_rep_expresso(IReparacoesLN model) {
        String[] nomesRep = new String[] {
                "Trocar ecra telemovel",
                "Mudar bateria telemovel",
                "Trocar SSD",
                "Instalar SO"
        };
        try {
            model.registarRepExpresso(nomesRep[0], 40, 75.0);
            model.registarRepExpresso(nomesRep[1], 25, 56.2);
            model.registarRepExpresso(nomesRep[2], 30, 150.0);
            model.registarRepExpresso(nomesRep[3], 60, 40.0);
        } catch (ReparacaoExpressoJaExisteException e1) {
        }

        for (int i = 1; i < 10; i++) {
            try {
                model.addRepExpresso(i + "", nomesRep[i % 4], ((i % 2) == 0 ? 7 : 8) + "");
            } catch (EquipamentoNaoExisteException | ReparacaoNaoExisteException | NaoExisteDisponibilidadeException
                    | ColaboradorNaoTecnicoException | ColaboradorNaoExisteException | TecnicoNaoTemAgendaException
                    | ColaboradorNaoFuncBalcao e) {
            }
        }
    }
}
