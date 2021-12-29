package g31.ReparacoesUI;

import g31.Middleware.ReparacaoExpressoJaExisteException;
import g31.Middleware.TecnicoJaTemAgendaException;
import g31.Middleware.TipoColaboradorErradoException;
import g31.ReparacoesLN.SSColaboradores.FuncionarioBalcao;
import g31.ReparacoesLN.SSColaboradores.Gestor;
import g31.ReparacoesLN.SSColaboradores.Tecnico;

import java.time.LocalDateTime;

import static g31.ReparacoesUI.MyUI.model;
import static g31.ReparacoesUI.MyUI.scin;

public class MenuGestor {
    private MenuColabEspecializado mce;

    public MenuGestor(){
        mce = new MenuColabEspecializado();
    }

    public void menuGestor() {
        Menu menu = new Menu(new String[] {
                "Registar Colaborador",
                "Listar reparações detalhadas por técnico",
                "Listar reparações resumidas por técnico",
                "Listar fluxo de trabalho do funcionário de balcão",
                "Menu Colaborador Especializado"
        });

        // Registar os handlers das transições
        menu.setHandler(1, this::registaColaborador);
        menu.setHandler(2, this::listarDetalhadas);
        menu.setHandler(3, this::listarResumidas);
        menu.setHandler(4, this::listarFluxoFunc);
        menu.setHandler(5, () -> mce.menuColabEspecial());

        menu.run();
    }

    private void registaColaborador() {
        try {
            System.out.println("Nome do Colaborador: ");
            String nome = scin.nextLine();
            System.out.println("Função do Colaborador: \n" +
                    "1 - Gestor\n 2 - Técnico\n 3 - Funcionario de Balcao");
            int tipo = scin.nextInt();
            switch (tipo) {
                case 1:
                    model.registaColaborador(nome, Gestor.class);
                    break;
                case 2:
                    model.registaColaborador(nome, Tecnico.class);
                    break;
                case 3:
                    model.registaColaborador(nome, FuncionarioBalcao.class);
            }
            System.out.println("Colaborador adicionado");
        } catch (TecnicoJaTemAgendaException | TipoColaboradorErradoException e) {
            e.printStackTrace();
        }
    }

    private void listarDetalhadas() {
        System.out.println("Mês que pretende ser avaliado: ");
        int mes = scin.nextInt();
        System.out.println("Ano: ");
        int ano = scin.nextInt();
        LocalDateTime data = LocalDateTime.of(ano, mes, 1, 1, 1, 1, 1);
        System.out.println(model.getReparacoesMes(data));
    }

    private void listarResumidas() {
        System.out.println("Mês que pretende ser avaliado: ");
        int mes = scin.nextInt();
        System.out.println("Ano: ");
        int ano = scin.nextInt();
        LocalDateTime data = LocalDateTime.of(ano, mes, 1, 1, 1, 1, 1);
        System.out.println(model.getReparacoesMes(data));
    }

    private void listarFluxoFunc() {
        System.out.println("Data de inicio da avaliação: \n Ano :");
        int anoDe = scin.nextInt();
        System.out.println("Mes :");
        int mesDe = scin.nextInt();
        System.out.println("Dia :");
        int diaDe = scin.nextInt();
        LocalDateTime dataDe = LocalDateTime.of(anoDe, mesDe, diaDe, 1, 1, 1, 1);
        System.out.println("Data do fim da avaliação: \n Ano :");
        int anoAte = scin.nextInt();
        System.out.println("Mes :");
        int mesAte = scin.nextInt();
        System.out.println("Dia :");
        int diaAte = scin.nextInt();
        LocalDateTime dataAte = LocalDateTime.of(anoAte, mesAte, diaAte, 1, 1, 1, 1);
        System.out.println("Recebidos :\n" + model.getEquipRecebidos(dataDe, dataAte));
        System.out.println("Entregues :\n" + model.getEquipEntregue(dataDe, dataAte));

    }
}
