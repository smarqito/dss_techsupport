package g31.ReparacoesUI;

import g31.Middleware.TecnicoJaTemAgendaException;
import g31.Middleware.TipoColaboradorErradoException;
import g31.ReparacoesLN.SSClientes.Equipamento;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.FuncionarioBalcao;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.Gestor;
import g31.ReparacoesLN.SSColaboradores.Colaboradores.Tecnico;
import g31.ReparacoesLN.SSReparacoes.Orcamento.Orcamento;
import g31.ReparacoesLN.SSReparacoes.PlanoTrabalho.PassoReparacao;
import g31.ReparacoesLN.SSReparacoes.Reparacao.Reparacao;
import g31.ReparacoesLN.SSReparacoes.Reparacao.ReparacaoExpresso;
import g31.ReparacoesLN.SSReparacoes.Reparacao.ReparacaoProgramada;
import g31.ReparacoesLN.SSReparacoes.Reparacao.ReparacoesPorMes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
                "Menu Colaborador Especializado",
                "Dar baixa de equipamentos",
                "Arquivar orçamentos"
        });

        // Registar os handlers das transições
        menu.setHandler(1, this::registaColaborador);
        menu.setHandler(2, this::listarDetalhadas);
        menu.setHandler(3, this::listarResumidas);
        menu.setHandler(4, this::listarFluxoFunc);
        menu.setHandler(5, () -> mce.menuColabEspecial());
        menu.setHandler(6, this::darBaixaEquipamentos);
        menu.setHandler(7, this::arquivarOrcamentos);


        menu.run();
    }


    private void registaColaborador() {
        try {
            System.out.println("Nome do Colaborador: ");
            String nome = scin.nextLine();
            System.out.println("Função do Colaborador: \n" +
                    "1 - Gestor\n 2 - Técnico\n 3 - Funcionario de Balcao");
            int tipo = this.getInt();
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
        } catch (TecnicoJaTemAgendaException e) {
            System.out.println("Técnico já tem agenda");
        } catch (TipoColaboradorErradoException e) {
            System.out.println("Classe de colaborador inválida");
        }
    }

    private void listarResumidas() {
        System.out.println("Mês que pretende ser avaliado: ");
        int mes = this.getInt();
        System.out.println("Ano: ");
        int ano = this.getInt();
        LocalDate data = LocalDate.of(ano, mes, 1);
        Map<Tecnico, ReparacoesPorMes> listagem = model.getReparacoesMes(data.atStartOfDay());
        for(Map.Entry<Tecnico, ReparacoesPorMes> m : listagem.entrySet()){
            System.out.println("O técnico " + m.getKey().getNome() + " com o id " + m.getKey().getId() + m.getValue().resumoReparacoes());
        }
    }

    private void listarDetalhadas() {
        System.out.println("Mês que pretende ser avaliado: ");
        int mes = this.getInt();
        System.out.println("Ano: ");
        int ano = this.getInt();
        LocalDate data = LocalDate.of(ano, mes, 1);
        Map<Tecnico, ReparacoesPorMes> listagem = model.getReparacoesMes(data.atStartOfDay());
        for(Map.Entry<Tecnico, ReparacoesPorMes> m : listagem.entrySet()){
            System.out.println("O técnico " + m.getKey().getNome() + " com o id " + m.getKey().getId() + "realizou as reparações:");
            for(Reparacao rep : m.getValue().getReps()){
                if (rep.getClass().getSimpleName().equals(ReparacaoProgramada.class.getSimpleName())){
                    System.out.println("Reparação programada com os seguintes passos:");
                    System.out.println(((ReparacaoProgramada) rep).getPlano().descricaoPassosRealizados());
                }
                else {
                    System.out.println("Reparação expresso " + ((ReparacaoExpresso)rep).getNome());
                }
            }
        }
    }

    private void listarFluxoFunc() {
        System.out.println("Data de inicio da avaliação: \n Ano :");
        int anoDe = this.getInt();
        System.out.println("Mes :");
        int mesDe = this.getInt();
        System.out.println("Dia :");
        int diaDe = this.getInt();
        LocalDate dataDe = LocalDate.of(anoDe, mesDe, diaDe);
        System.out.println("Data do fim da avaliação: \n Ano :");
        int anoAte = this.getInt();
        System.out.println("Mes :");
        int mesAte = this.getInt();
        System.out.println("Dia :");
        int diaAte = this.getInt();
        LocalDate dataAte = LocalDate.of(anoAte, mesAte, diaAte);
        Map<FuncionarioBalcao, List<Equipamento>> recebidos = model.getEquipRecebidos(dataDe.atStartOfDay(), dataAte.atStartOfDay());
        Map<FuncionarioBalcao, List<Equipamento>> entregues = model.getEquipEntregue(dataDe.atStartOfDay(), dataAte.atStartOfDay());
        System.out.println("Entre os dias " + dataDe.toString() + " e " + dataAte.toString() + ": ");
        for (Map.Entry<FuncionarioBalcao, List<Equipamento>> m : recebidos.entrySet()){
            System.out.println("O funcionário " + m.getKey().getNome() + " com o id " + m.getKey().getId() + " realizou " + m.getValue().size() + " receções!");
        }
        for (Map.Entry<FuncionarioBalcao, List<Equipamento>> m : entregues.entrySet()){
            System.out.println("O funcionário " + m.getKey().getNome() + " com o id " + m.getKey().getId() + " realizou " + m.getValue().size() + " entregas!");
        }

    }

    private void arquivarOrcamentos() {
        List<Orcamento> orcs = model.arquivaOrcamentos();
        System.out.println("Lista dos identificadores dos orçamentos arquivados:");
        for (Orcamento o : orcs){
            System.out.println(o.getID());
        }
        System.out.println("Orçamentos arquivados com sucesso");
    }

    private void darBaixaEquipamentos() {
        List<Equipamento> equips = model.darBaixaEquipamentos();
        System.out.println("Lista dos identificadores dos equipamentos dados como abandonados:");
        for (Equipamento e : equips){
            System.out.println(e.getId());
        }
        System.out.println("Equipamentos foram dados como abandonados com sucesso");
    }

    private int getInt(){
        while (!scin.hasNextInt()) {
            scin.next();
            System.out.println("Insira uma quantidade válida");
        }
        return scin.nextInt();
    }

    private double getDouble(){
        while (!scin.hasNextDouble()) {
            scin.next();
            System.out.println("Insira uma quantidade válida");
        }
        return scin.nextDouble();
    }
}
