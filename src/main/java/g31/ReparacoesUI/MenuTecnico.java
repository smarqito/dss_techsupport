package g31.ReparacoesUI;

import g31.Middleware.*;
import g31.ReparacoesLN.SSColaboradores.Agenda.AgendaPorDia;
import g31.ReparacoesLN.SSColaboradores.Agenda.EntradaAgenda;
import g31.ReparacoesLN.SSReparacoes.Orcamento.OrcamentoEstado;
import g31.ReparacoesLN.SSReparacoes.Reparacao.Reparacao;
import g31.ReparacoesLN.SSReparacoes.Reparacao.ReparacaoEstado;
import g31.ReparacoesLN.SSReparacoes.Reparacao.ReparacaoProgramada;
import g31.ReparacoesLN.SSReparacoes.Orcamento.Orcamento;

import java.time.LocalDate;

import static g31.ReparacoesUI.MyUI.model;
import static g31.ReparacoesUI.MyUI.scin;

public class MenuTecnico {
    private MenuColabEspecializado mce;

    public MenuTecnico() {
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

        // Registar os handlers das transições
        menu.setHandler(1, () -> fazerOrcamento(tecId));
        menu.setHandler(2, () -> realizarReparacao(tecId));
        menu.setHandler(3, () -> mce.menuColabEspecial());
        menu.setHandler(4, () -> verificarAgenda(tecId));

        menu.run();
    }

    private void fazerOrcamento(String tecId) {

        try {
            Orcamento orc = model.getOrcamentoMaisAntigo();

            MyUI.printLine();
            System.out.println("Orcamento: " + orc.getID() + "\n" + "Descrição: " + orc.getDescrProb() + "\n"
                    + "Equipamento: " + orc.getEquipamento().toString() + "\n");

            Menu menu = new Menu(new String[] {
                    "Definir plano de trabalhos",
                    "Gerar Orcamento"
            });

            // Registar os handlers das transições
            menu.setHandler(1, () -> definirPlano(orc.getID(), tecId));
            menu.setHandler(2, () -> gerarOrc(orc.getID(), tecId));

            menu.run();

        } catch (NaoExisteOrcamentosAtivosException e) {

            MyUI.printLine();
            System.out.println("Não existem orçamentos ativos para trabalhar!");
        }

    }

    private void definirPlano(String orcId, String tecId) {

        Orcamento orc;
        try {
            orc = model.getOrcamento(orcId);
            MyUI.printLine();
            System.out.println("Plano de Trabalhos atual: " + orc.getPT().toString());

        } catch (OrcamentoNaoExisteException e) {

            MyUI.printLine();
            System.out.println("O orçamento com o identificador " + orcId + " não existe!");
        }

        Menu menu = new Menu(new String[] {
                "Adicionar passo de reparação",
                "Registar comunicação com o cliente"
        });

        // Registar os handlers das transições
        menu.setHandler(1, () -> mce.adicionarPasso(orcId));
        menu.setHandler(2, () -> registarComunic(orcId, tecId));

        menu.run();
    }

    private void gerarOrc(String orcId, String tecID) {

        Orcamento orc;
        try {
            orc = model.getOrcamento(orcId);
            MyUI.printLine();
            System.out.println("Orçamento mais antigo: " + orc.generateResume());

        } catch (OrcamentoNaoExisteException e) {

            MyUI.printLine();
            System.out.println("O orçamento com o identificador " + orcId + " não existe!");
        }

        try {
            model.alterarEstadoOrc(orcId, OrcamentoEstado.enviado);

        } catch (EstadoOrcNaoEValidoException e) {
            MyUI.printLine();
            System.out.println("Estado do orçamento " + OrcamentoEstado.enviado + "não válido!");

        } catch (OrcamentoNaoExisteException e) {
            MyUI.printLine();
            System.out.println("O orçamento com o identificador " + orcId + " não existe!");

        } catch (ColaboradorNaoTecnicoException e) {
            MyUI.printLine();
            System.out.println("O colaborador associado não é um Técnico!");

        } catch (ColaboradorNaoExisteException e) {
            MyUI.printLine();
            System.out.println("O colaborador associado não existe!");

        } catch (NaoExisteDisponibilidadeException e) {
            MyUI.printLine();
            System.out.println("Não há disponibilidade do colaborador associado para realizar o orçamento!");

        } catch (TecnicoNaoTemAgendaException e) {
            MyUI.printLine();
            System.out.println("O colaborador associado não possui uma agenda!");
        }

    }

    private void registarComunic(String orcId, String tecId) {

        MyUI.printLine();
        System.out.println("Insira comentário sobre a comunicação: ");
        String comentario = scin.nextLine();

        try {
            model.comunicarErro(orcId, comentario, tecId);

        } catch (OrcamentoNaoExisteException e) {
            MyUI.printLine();
            System.out.println("O orçamento com identificador " + orcId + " não existe!");

        } catch (ColaboradorNaoTecnicoException e) {
            MyUI.printLine();
            System.out.println("O colaborador com identificador " + tecId + " não é um Técnico!");

        } catch (ColaboradorNaoExisteException e) {
            MyUI.printLine();
            System.out.println("O colaborador com identificador " + tecId + " não existe!");
        }
    }

    private void realizarReparacao(String tecId) {

        try {
            if (model.getAgendaDia(LocalDate.now(), tecId).getEntradaAgenda().size() == 0) {
                MyUI.printLine();
                System.out.println("O técnico " + tecId + " não tem mais tarefas para o dia!");

            } else {
                MyUI.printLine();
                System.out.println("Insira o identificador da reparação: ");
                String repId = scin.nextLine();

                try {
                    Reparacao rep = model.getReparacao(repId);
                    if (!rep.getUltimoEstado().getEstado().equals(ReparacaoEstado.reparado)) {

                        if (rep.getClass().getSimpleName().equals(ReparacaoProgramada.class.getSimpleName())) {

                            Menu menu = new Menu(new String[] {
                                    "Realizar passo da reparação",
                                    "Registar comunicação com o cliente",
                                    "Cancelar reparação"
                            });

                            ReparacaoProgramada repProgr = (ReparacaoProgramada) rep;

                            menu.setPreCondition(1, () -> repProgr.getPlano().haMaisPassos());
                            // Registar os handlers das transições
                            menu.setHandler(1, () -> realizarPasso(repId));
                            menu.setHandler(2, () -> comunicarErro(repId, tecId));
                            menu.setHandler(3, () -> cancelarRep(repId));

                            menu.run();

                        } else {
                            Menu menu = new Menu(new String[] {
                                    "Registar conclusão"
                            });

                            menu.setPreCondition(1,
                                    () -> !rep.getUltimoEstado().getEstado().equals(ReparacaoEstado.reparado));
                            menu.setHandler(1, () -> registarConclusao(repId));
                            menu.runOnce();
                        }
                    } else {
                        MyUI.printLine();
                        System.out.println("Reparacao " + repId + " já foi concluída!");
                    }
                } catch (ReparacaoNaoExisteException e) {
                    MyUI.printLine();
                    System.out.println("A reparação com identificador " + repId + " não existe!");
                }
            }
        } catch (TecnicoNaoTemAgendaException e1) {
            MyUI.printLine();
            System.out.println("O Técnico com identificador " + tecId + " não tem agenda!");
        }

    }

    private void verificarAgenda(String tecId) {

        AgendaPorDia ag;
        try {
            ag = model.getAgendaDia(LocalDate.now(), tecId);

            for (EntradaAgenda ea : ag.getEntradaAgenda()) {
                MyUI.printLine();
                System.out.println("Reparacao com id: " + ea.getDetalhes());
                System.out.println("Começa às: " + ea.getInicio());
                System.out.println("Com duração de: " + ea.getDuracao() + "\n");
            }
        } catch (TecnicoNaoTemAgendaException e) {
            MyUI.printLine();
            System.out.println("O técnico com identificador " + tecId + " não tem agenda!");
        }
    }

    private void registarConclusao(String repId) {

        try {
            model.alterarEstadoRep(repId, ReparacaoEstado.reparado);

        } catch (ReparacaoNaoExisteException e) {
            MyUI.printLine();
            System.out.println("A reparação com identificador " + repId + " não existe!");
        }
    }

    private void realizarPasso(String repId) {

        try {
            MyUI.printLine();
            System.out.println("Insira custo efetivo: ");
            double custo = scin.nextDouble();
            MyUI.printLine();
            System.out.println("Insira o tempo gasto: ");
            int tempo = scin.nextInt();
            model.registaPasso(repId, tempo, custo);
        } catch (ReparacaoNaoExisteException e) {
            MyUI.printLine();
            System.out.println("A reparação com identificador " + repId + " não existe!");
        }
    }

    private void comunicarErro(String repId, String tecId) {

        MyUI.printLine();
        System.out.println("Comentário sobre a comunicação: ");
        String msg = scin.nextLine();

        try {
            model.registaContacto(repId, tecId, msg);
        } catch (ReparacaoNaoExisteException e) {
            MyUI.printLine();
            System.out.println("A reparação com identificador " + repId + " não existe!");

        } catch (ColaboradorNaoTecnicoException e) {
            MyUI.printLine();
            System.out.println("O colaborador com identificador " + tecId + " não é um Técnico!");

        } catch (ColaboradorNaoExisteException e) {
            MyUI.printLine();
            System.out.println("O colaborador com identificador " + tecId + " não existe!");
        }
    }

    private void cancelarRep(String repId) {
        try {
            MyUI.printLine();
            System.out.println("Insira as horas gastas: ");
            int tempo = scin.nextInt();
            model.alterarEstadoRep(repId, ReparacaoEstado.cancelada, "tempo gasto: " + tempo);
        } catch (ReparacaoNaoExisteException e) {
            MyUI.printLine();
            System.out.println("A reparação com identificador " + repId + " não existe!");
        }
    }
}
