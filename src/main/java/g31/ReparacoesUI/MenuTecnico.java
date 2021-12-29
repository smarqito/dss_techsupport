package g31.ReparacoesUI;

import g31.Middleware.*;
import g31.ReparacoesLN.SSColaboradores.Agenda.AgendaPorDia;
import g31.ReparacoesLN.SSColaboradores.Agenda.EntradaAgenda;
import g31.ReparacoesLN.SSReparacoes.Orcamento.OrcamentoEstado;
import g31.ReparacoesLN.SSReparacoes.Reparacao.ReparacaoEstado;
import g31.ReparacoesLN.SSReparacoes.Reparacao.ReparacaoProgramada;

import java.time.LocalDate;

import static g31.ReparacoesUI.MyUI.model;
import static g31.ReparacoesUI.MyUI.scin;

public class MenuTecnico {
    private MenuColabEspecializado mce;

    public MenuTecnico(){
        this.mce = new MenuColabEspecializado();
    }

    public void menuTecnico(String tecId) {
        Menu menu = new Menu(new String[] {
                "Fazer orçamento",
                "Realizar reparação",
                "Menu Colaborador Especializado",
                "Verificar agenda"
        });

        // pré-condições
        menu.setPreCondition(1, () -> model.getOrcamentosAtivos().size() > 0);
        // agenda nao vazia

        // Registar os handlers das transições
        menu.setHandler(1, () -> fazerOrcamento(tecId));
        menu.setHandler(2, () -> realizarReparacao(tecId));
        menu.setHandler(3, () -> mce.menuColabEspecial());
        menu.setHandler(4, () -> verificarAgenda(tecId));

        menu.run();
    }

    private void fazerOrcamento(String tecId) {

        String orcId = null; // model.getOrcamentoMaisAntigo();

        Menu menu = new Menu(new String[] {
                "Definir plano de trabalhos",
                "Gerar Orcamento"
        });

        // Registar os handlers das transições
        menu.setHandler(1, () -> definirPLano(orcId, tecId));
        menu.setHandler(2, () -> gerarOrc(orcId));

        menu.runOnce();
    }

    private void definirPLano(String orcId, String tecId) {
        // print do orcamento
        Menu menu = new Menu(new String[] {
                "Adicionar passo de reparação",
                "Registar comunicação com o cliente"
        });

        // pré-condições

        // Registar os handlers das transições
        menu.setHandler(1, () -> mce.adicionarPasso(orcId));
        menu.setHandler(2, () -> registarComunic(orcId, tecId));

        menu.run();
    }



    private void gerarOrc(String orcId) {
        // print do orcamento
        try {
            model.alterarEstadoOrc(orcId, OrcamentoEstado.enviado);
        } catch (EstadoOrcNaoEValidoException | OrcamentoNaoExisteException | ColaboradorNaoTecnicoException
                | ColaboradorNaoExisteException | NaoExisteDisponibilidadeException | TecnicoNaoTemAgendaException e) {
            e.printStackTrace();
        }
    }

    private void registarComunic(String orcId, String tecId) {
        try {
            System.out.println("Comentário sobre a comunicação: ");
            String comentario = scin.nextLine();
            model.comunicarErro(orcId, tecId, comentario);
        } catch (OrcamentoNaoExisteException | ColaboradorNaoExisteException | ColaboradorNaoTecnicoException e) {
            e.printStackTrace();
        }
    }

    private void realizarReparacao(String tecId) {
        System.out.println("Insira o identificador da reparação: ");
        String repId = scin.nextLine();
        try {
            if (model.getReparacao(repId).getClass().equals(ReparacaoProgramada.class)) {
                Menu menu = new Menu(new String[] {
                        "Realizar passo da reparação",
                        "Registar comunicação com o cliente",
                        "Cancelar reparação"
                });

                // Registar os handlers das transições
                menu.setHandler(1, () -> realizarPasso(repId));
                menu.setHandler(2, () -> comunicarErro(repId, tecId));
                menu.setHandler(3, () -> cancelarRep(repId));

                menu.run();
            } else {
                Menu menu = new Menu(new String[] {
                        "Registar conclusão"
                });

                menu.setHandler(1, () -> registarConclusao(repId));

            }
        } catch (ReparacaoNaoExisteException e) {
            e.printStackTrace();
        }
    }

    private void verificarAgenda(String tecId) {
        AgendaPorDia ag;
        try {
            ag = model.getAgendaDia(LocalDate.now(), tecId);
            for (EntradaAgenda ea : ag.getEntradaAgenda()) {
                System.out.println("Reparacao com id: " + ea.getDetalhes());
                System.out.println("Começa às: " + ea.getInicio());
                System.out.println("Com duração de: " + ea.getDuracao());
            }
        } catch (TecnicoNaoTemAgendaException e) {
            e.printStackTrace();
        }
    }

    private void registarConclusao(String repId) {
        try {
            model.alterarEstadoRep(repId, ReparacaoEstado.reparado);
        } catch (ReparacaoNaoExisteException e) {
            e.printStackTrace();
        }
    }

    private void realizarPasso(String repId) {
        try {
            System.out.println("Insira custo efetivo: ");
            double custo = scin.nextDouble();
            System.out.println("Insira o tempo gasto: ");
            int tempo = scin.nextInt();
            model.registaPasso(repId, tempo, custo);
        } catch (ReparacaoNaoExisteException e) {
            e.printStackTrace();
        }
    }

    private void comunicarErro(String repId, String tecId) {
        try {
            System.out.println("Comentário sobre a comunicação: ");
            String msg = scin.nextLine();
            model.registaContacto(repId, tecId, msg);
        } catch (ReparacaoNaoExisteException | ColaboradorNaoTecnicoException | ColaboradorNaoExisteException e) {
            e.printStackTrace();
        }
    }

    private void cancelarRep(String repId) {
        try {
            System.out.println("Insira as horas gastas: ");
            int tempo = scin.nextInt();
            model.alterarEstadoRep(repId, ReparacaoEstado.cancelada, "tempo gasto: " + tempo);
        } catch (ReparacaoNaoExisteException e) {
            e.printStackTrace();
        }
    }
}
