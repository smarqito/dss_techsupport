package g31.ReparacoesUI;

import g31.Middleware.OrcamentoNaoExisteException;
import g31.Middleware.PassoJaExisteException;
import g31.Middleware.ReparacaoExpressoJaExisteException;
import g31.ReparacoesLN.SSReparacoes.Orcamento.Orcamento;

import java.util.InputMismatchException;

import static g31.ReparacoesUI.MyUI.model;
import static g31.ReparacoesUI.MyUI.scin;

public class MenuColabEspecializado {
    public void menuColabEspecial() {
        Menu menu = new Menu(new String[] {
                "Criar Reparação Expresso",
                "Criar Passo de Reparação"
        });

        menu.setPreCondition(2, () -> model.getOrcamentosAtivos().size() == 0);

        // Registar os handlers das transições
        menu.setHandler(1, this::criarRepXpresso);
        menu.setHandler(2, this::criarPassoRep);

        menu.runOnce();
    }

    private void criarRepXpresso() {
      
        System.out.println("Insira o nome da reparação expresso a adicionar: ");
        String nome = scin.nextLine();
        System.out.println("Insira o preço: ");
        double preco = this.getDouble();
        System.out.println("Insira o tempo estimado");
        int tempo = this.getInt();

        try {

            model.registarRepExpresso(nome, tempo, preco);
            System.out.println("Reparação expresso criada com sucesso!");

        } catch (ReparacaoExpressoJaExisteException e) {

            System.out.println("A reparação expresso " + nome + " já existe!");

        }
    }

    private void criarPassoRep() {

        System.out.println("Insira o identificador do orçamento a atualizar: ");
        String orcId = scin.nextLine();

        try {
            Orcamento orc = model.getOrcamento(orcId);
            System.out.println("Plano de trabalhos do orçamento: "+orc.getPT().toString());

        } catch (OrcamentoNaoExisteException e) {

            System.out.println("O orçamento com identificador"+orcId+"não existe!");            
        }

        this.adicionarPasso(orcId);
    }

    public void adicionarPasso(String orcId) {
        System.out.println("Insira nome do passo a adicionar: ");
        String nome = scin.nextLine(); //corrigir erro de passar este À frente
        if(nome.equals(""))
            nome = scin.nextLine();
        System.out.println("Insira o material a adicionar: ");
        String material = scin.nextLine();
        System.out.println("Insira quantidade de material: ");
        int quantidade = this.getInt();
        System.out.println("Insira custo total do material: ");
        double custo = this.getDouble();
        System.out.println("Insira o tempo estimado: ");
        int tempo = this.getInt();
        try {
            model.criarPasso(orcId, nome, material, tempo, quantidade, custo);
            System.out.println("Passo " + nome + " adicionado ao orçamento com o id: " + orcId);
        } catch (PassoJaExisteException e) {
            System.out.println("O passo "+nome+" já existe no plano de trabalhos!");
        }
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
